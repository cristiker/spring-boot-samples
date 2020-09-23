package com.cristik.common.http;

/**
 * Contains constant definitions for the HTTP header field names. See:
 * <ul>
 * <li><a href="http://www.ietf.org/rfc/rfc2109.txt">RFC 2109</a>
 * <li><a href="http://www.ietf.org/rfc/rfc2183.txt">RFC 2183</a>
 * <li><a href="http://www.ietf.org/rfc/rfc2616.txt">RFC 2616</a>
 * <li><a href="http://www.ietf.org/rfc/rfc2965.txt">RFC 2965</a>
 * <li><a href="http://www.ietf.org/rfc/rfc5988.txt">RFC 5988</a>
 * </ul>
 *
 * @author Kurt Alfred Kluever
 * @since 11.0
 */
public final class HttpHeader {
    /**
     * HTTP Request and Response header fields
     */
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String DATE = "Date";
    public static final String PRAGMA = "Pragma";
    public static final String VIA = "Via";
    public static final String WARNING = "Warning";
    /**
     * HTTP Request header fields
     */
    public static final String ACCEPT = "Accept";
    public static final String ACCEPT_CHARSET = "Accept-Charset";
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
    public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONNECTION = "Connection";
    public static final String COOKIE = "Cookie";
    public static final String EXPECT = "Expect";
    public static final String FROM = "From";
    public static final String FOLLOW_ONLY_WHEN_PRERENDER_SHOWN = "Follow-Only-When-Prerender-Shown";
    public static final String HOST = "Host";
    public static final String IF_MATCH = "If-Match";
    public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
    public static final String IF_NONE_MATCH = "If-None-Match";
    public static final String IF_RANGE = "If-Range";
    public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
    public static final String LAST_EVENT_ID = "Last-Event-ID";
    public static final String MAX_FORWARDS = "Max-Forwards";
    public static final String ORIGIN = "Origin";
    public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
    public static final String RANGE = "Range";
    public static final String REFERER = "Referer";
    public static final String TE = "TE";
    public static final String UPGRADE = "Upgrade";
    public static final String USER_AGENT = "User-Agent";
    /**
     * HTTP Response header fields
     */
    public static final String ACCEPT_RANGES = "Accept-Ranges";
    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
    public static final String AGE = "Age";
    public static final String ALLOW = "Allow";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String CONTENT_LANGUAGE = "Content-Language";
    public static final String CONTENT_LOCATION = "Content-Location";
    public static final String CONTENT_MD5 = "Content-MD5";
    public static final String CONTENT_RANGE = "Content-Range";
    /**
     * The HTTP <a href="http://w3.org/TR/CSP/#content-security-policy-header-field">
     * {@code Content-Security-Policy}</a> header field name.
     *
     * @since 15.0
     */
    public static final String CONTENT_SECURITY_POLICY = "Content-Security-Policy";
    /**
     * The HTTP <a href="http://w3.org/TR/CSP/#content-security-policy-report-only-header-field">
     * {@code Content-Security-Policy-Report-Only}</a> header field name.
     *
     * @since 15.0
     */
    public static final String CONTENT_SECURITY_POLICY_REPORT_ONLY = "Content-Security-Policy-Report-Only";
    public static final String ETAG = "ETag";
    public static final String EXPIRES = "Expires";
    public static final String LAST_MODIFIED = "Last-Modified";
    public static final String LINK = "Link";
    public static final String LOCATION = "Location";
    public static final String P3P = "P3P";
    public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
    /**
     * The HTTP {@code Refresh} header field name. Non-standard header supported by most browsers.
     */
    public static final String REFRESH = "Refresh";
    public static final String RETRY_AFTER = "Retry-After";
    public static final String SERVER = "Server";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String SET_COOKIE2 = "Set-Cookie2";
    /**
     * The HTTP <a href="http://tools.ietf.org/html/rfc6797#section-6.1">
     * {@code Strict-Transport-Security}</a> header field name.
     *
     * @since 15.0
     */
    public static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";
    /**
     * The HTTP <a href="http://www.w3.org/TR/resource-timing/#cross-origin-resources">
     * {@code Timing-Allow-Origin}</a> header field name.
     *
     * @since 15.0
     */
    public static final String TIMING_ALLOW_ORIGIN = "Timing-Allow-Origin";
    public static final String TRAILER = "Trailer";
    public static final String TRANSFER_ENCODING = "Transfer-Encoding";
    public static final String VARY = "Vary";
    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    /**
     * Common, non-standard HTTP header fields
     */
    public static final String DNT = "DNT";
    public static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";
    public static final String X_DO_NOT_TRACK = "X-Do-Not-Track";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";
    public static final String X_FRAME_OPTIONS = "X-Frame-Options";
    public static final String X_POWERED_BY = "X-Powered-By";
    /**
     * The HTTP <a href="http://tools.ietf.org/html/draft-evans-palmer-key-pinning">
     * {@code Public-Key-Pins}</a> header field name.
     *
     * @since 15.0
     */
    public static final String PUBLIC_KEY_PINS = "Public-Key-Pins";
    /**
     * The HTTP <a href="http://tools.ietf.org/html/draft-evans-palmer-key-pinning">
     * {@code Public-Key-Pins-Report-Only}</a> header field name.
     *
     * @since 15.0
     */
    public static final String PUBLIC_KEY_PINS_REPORT_ONLY = "Public-Key-Pins-Report-Only";
    public static final String X_REQUESTED_WITH = "X-Requested-With";
    public static final String X_USER_IP = "X-User-IP";
    public static final String X_XSS_PROTECTION = "X-XSS-Protection";

    private HttpHeader() {
    }
}
