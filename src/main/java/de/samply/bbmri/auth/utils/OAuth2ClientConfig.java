package de.samply.bbmri.auth.utils;

import de.samply.bbmri.auth.rest.Scope;
import de.samply.common.config.OAuth2Client;
import de.samply.common.config.OAuth2Client.AdditionalHostnames.Hostname;
import de.samply.string.util.StringUtil;
import de.samply.string.util.StringUtil.Builder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * A small helper used to generate URLs to the identity provider.
 */
public class OAuth2ClientConfig {

  /**
   * Todo.
   * @param config Todo.
   * @param serverName Todo.
   * @return Todo.
   */
  public static String getHost(OAuth2Client config, String serverName) {
    String host = config.getHost();

    /**
     * There might be an additional hostname that should be used. Check the servername for
     * equality and change the variable host accordingly.
     */
    if (config.getAdditionalHostnames() != null) {
      for (Hostname hostname : config.getAdditionalHostnames().getHostname()) {
        if (serverName.toLowerCase().equals(hostname.getIfServernameEquals())) {
          host = hostname.getHost();
        }
      }
    }

    return host;
  }

  /**
   * Constructs the URL to the OAuth2 provider, using the provided configuration. The redirect is
   * set to "http(s)?://$serverName(:$port)?/$contextPath/$redirectUrl"
   *
   * @param config      the OAuth2 configuration
   * @param scheme      the scheme (http|https) from the HTTP request
   * @param serverName  the server name from the HTTP request
   * @param port        the port (80, 443, 8080 ...) from the HTTP request
   * @param contextPath the context path for the application ("/", "/mdr-gui") from the HTTP
   *                    request
   * @param redirectUrl the redirect URL inside the context path
   * @param scopes      a list of scopes separated by a whitespace
   * @return the URL
   * @throws UnsupportedEncodingException UnsupportedEncodingException
   */
  public static String getRedirectUrl(OAuth2Client config, String scheme,
      String serverName, int port, String contextPath, String redirectUrl,
      Scope... scopes) throws UnsupportedEncodingException {
    return getRedirectUrl(config, scheme, serverName, port, contextPath, redirectUrl, null, scopes);
  }

  /**
   * Constructs the URL to the OAuth2 provider, using the provided configuration. The redirect is
   * set to "http(s)?://$serverName(:$port)?/$contextPath/$redirectUrl"
   *
   * @param config      the OAuth2 configuration
   * @param scheme      the scheme (http|https) from the HTTP request
   * @param serverName  the server name from the HTTP request
   * @param port        the port (80, 443, 8080 ...) from the HTTP request
   * @param contextPath the context path for the application ("/", "/mdr-gui") from the HTTP
   *                    request
   * @param redirectUrl the redirect URL inside the context path
   * @param state       the state parameter used to protect against cross site requests. Use null if
   *                    you don't want to use a state parameter.
   * @param scopes      a list of scopes separated by a whitespace
   * @return the URL
   * @throws UnsupportedEncodingException UnsupportedEncodingException
   */
  public static String getRedirectUrl(OAuth2Client config, String scheme,
      String serverName, int port, String contextPath, String redirectUrl,
      String state, Scope... scopes) throws UnsupportedEncodingException {
    StringBuilder builder;

    String host = getHost(config, serverName);

    ///TODO: remove hard coded uri's and read them from the configuration link
    if (host.contains("lifescience")) {
      builder = new StringBuilder(host + "/oauth2-as/oauth2-authz");
    } else {
      builder = new StringBuilder(host + "/oidc/authorize");
    }

    builder.append("?client_id=")
        .append(URLEncoder.encode(config.getClientId(), StandardCharsets.UTF_8.displayName()));
    builder.append("&scope=")
        .append(URLEncoder.encode(StringUtil.join(scopes, " ", new Builder<Scope>() {
          @Override
          public String build(Scope o) {
            return o.getIdentifier();
          }
        }), StandardCharsets.UTF_8.displayName()));
    String redirectUri = getLocalRedirectUrl(config, scheme, serverName, port, contextPath,
        redirectUrl);
    builder.append("&redirect_uri=")
        .append(URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.displayName()));

    if (!StringUtil.isEmpty(state)) {
      builder.append("&state=")
          .append(URLEncoder.encode(state, StandardCharsets.UTF_8.displayName()));
    }

    builder.append("&response_type=code");

    return builder.toString();
  }

  /**
   * Constructs the URL for the perun registration page, using the provided configuration. The
   * redirect is set to "http(s)?://$serverName(:$port)?/$contextPath/$redirectUrl"
   *
   * @param config      the OAuth2 configuration
   * @param scheme      the scheme (http|https) from the HTTP request
   * @param serverName  the server name from the HTTP request
   * @param port        the port (80, 443, 8080 ...) from the HTTP request
   * @param contextPath the context path for the application ("/", "/mdr-gui") from the HTTP
   *                    request
   * @param redirectUrl the redirect URL inside the context path
   * @param state       the state parameter used to protect against cross site requests. Use null if
   *                    you don't want to use a state parameter.
   * @param scopes      a list of scopes separated by a whitespace
   * @return the URL
   * @throws UnsupportedEncodingException UnsupportedEncodingException
   */
  public static String getRedirectUrlRegisterPerun(OAuth2Client config, String scheme,
      String serverName, int port, String contextPath, String redirectUrl,
      String state, Scope... scopes) throws UnsupportedEncodingException {
    String host = getHost(config, serverName);

    StringBuilder builder = new StringBuilder(host);
    builder.append("/registrar");
    builder.append("?client_id=")
        .append(URLEncoder.encode(config.getClientId(), StandardCharsets.UTF_8.displayName()));
    builder.append("&scope=")
        .append(URLEncoder.encode(StringUtil.join(scopes, " ", new Builder<Scope>() {
          @Override
          public String build(Scope o) {
            return o.getIdentifier();
          }
        }), StandardCharsets.UTF_8.displayName()));
    builder.append("&vo=bbmri");
    String redirectUri = getLocalRedirectUrl(config, scheme, serverName, port, contextPath,
        redirectUrl);
    builder.append("&targetnew=").append(redirectUri);
    builder.append("&targetexisting=").append(redirectUri);
    builder.append("&redirect_uri=").append(redirectUri);

    if (!StringUtil.isEmpty(state)) {
      builder.append("&state=")
          .append(URLEncoder.encode(state, StandardCharsets.UTF_8.displayName()));
    }

    builder.append("&response_type=code");

    return builder.toString();
  }


  /**
   * Creates a URL for the current host.
   *
   * @param config      the OAuth2 Configuration
   * @param scheme      the scheme (http|https)
   * @param serverName  the server name
   * @param port        the port (80, 443, 8080 ...)
   * @param contextPath the context path for the application ("/", "/mdr-gui")
   * @param redirectUrl the redirect URL inside the context path.
   * @return a {@link java.lang.String} object.
   */
  public static String getLocalRedirectUrl(OAuth2Client config, String scheme,
      String serverName, int port, String contextPath, String redirectUrl) {
    return getLocalRedirectUrl(scheme, serverName, port, contextPath, redirectUrl);
  }

  /**
   * Creates a URL for the current host.
   * @param scheme the scheme (http|https)
   * @param serverName the server name
   * @param port the port (80, 443, 8080 ...)
   * @param contextPath the context path for the application ("/", "/mdr-gui")
   * @param redirectUrl the redirect URL inside the context path.
   * @return a {@link java.lang.String} object.
   */
  public static String getLocalRedirectUrl(String scheme,
      String serverName, int port, String contextPath, String redirectUrl) {
    String strPort = (port == 80 || port == 443 ? "" : ":" + port);
    StringBuilder builder = new StringBuilder(scheme);
    builder.append("://").append(serverName).append(strPort)
        .append(contextPath).append(redirectUrl);
    return builder.toString();
  }

  /**
   * Returns the logout URL for Samply Auth.
   *
   * @param config           the OAuth2 Configuration
   * @param scheme           the scheme (http|https)
   * @param serverName       the server name
   * @param port             the port (80, 443, 8080 ...)
   * @param contextPath      the context path for the application ("/", "/mdr-gui")
   * @param localRedirectUrl the redirect URL inside the context path.
   * @return a {@link java.lang.String} object.
   * @throws UnsupportedEncodingException UnsupportedEncodingException
   */
  public static String getLogoutUrl(OAuth2Client config, String scheme,
      String serverName, int port, String contextPath, String localRedirectUrl)
      throws UnsupportedEncodingException {

    String redirect = getLocalRedirectUrl(config, scheme,
        serverName, port,
        contextPath, localRedirectUrl);

    String host = getHost(config, serverName);

    StringBuilder builder = new StringBuilder(host);
    builder.append("/logout.xhtml?redirect_uri=")
        .append(URLEncoder.encode(redirect, StandardCharsets.UTF_8.displayName()))
        .append("&client_id=")
        .append(URLEncoder.encode(config.getClientId(), StandardCharsets.UTF_8.displayName()));
    return builder.toString();
  }

}
