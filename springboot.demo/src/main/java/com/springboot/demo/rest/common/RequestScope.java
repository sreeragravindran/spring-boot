package com.springboot.demo.rest.common;

import com.springboot.demo.common.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;

public class RequestScope {
    private static final ThreadLocal<RequestContext> context = new ThreadLocal<>();
    private static RequestContext requestContext = new RequestContext("", "default-trace-id", "default-request-id", DateTimeUtils.getNowInUTC());

    private RequestScope(String accessToken, String traceId, String requestId, LocalDateTime dateTime) {
        RequestContext requestContext = new RequestContext(accessToken, traceId, requestId, dateTime);
        set(requestContext);
    }

    private static RequestContext get() {
        ///This is to handle scenarios where request context is not set. Like Application start-up fail etc.
        if (Objects.isNull(context.get())) {
            return requestContext;
        }
        return context.get();
    }

    public static String getRouteName() {
        String routeName = get().getRouteName();
        if (StringUtils.isEmpty(routeName)) {
            routeName = "NA";
        }
        return routeName;
    }

    public static void setRouteName(String routeName) {
        get().setRouteName(routeName);
    }

    public static String getAuthorisedId() {
        return get().getAuthorisedId();
    }

    public static void setAuthorisedId(String authorisedId) {
        get().setAuthorisedId(authorisedId);
    }

    public static int getConfidenceLevel() {
        return get().getConfidenceLevel();
    }

    public static void setConfidenceLevel(int confidenceLevel) {
        get().setConfidenceLevel(confidenceLevel);
    }

    public static String getTraceId() {
        return get().getTraceId();
    }

    public static String getRequestId() {
        return get().getRequestId();
    }

    public static int getSequence() {
        return get().getSequence();
    }

    public static LocalDateTime getDateTime() {
        return get().getDateTime();
    }

//    public static List<LoggingEventBase> getLoggingEvents() {
//        return get().getEvents();
//    }

//    public static void addLoggingEvent(LoggingEventBase loggingEventBase) {
//        get().addEvent(loggingEventBase);
//    }

    public static Object getRequestBody() { return get().getRequestBody(); }

    public static void setRequestBody(Object requestBody) { get().setRequestBody(requestBody); }

    public static boolean isBeta() {
        return get().isBeta();
    }

    public static void setBeta(boolean isBeta) {
        get().setBeta(isBeta);
    }

    static void unset() {
//        MDC.clear();
        context.remove();
    }

    //TODO why was this not public?
    public static String getAccessToken() {
        return get().getAccessToken();
    }

    public static RequestScopeBuilder builder() {
        return new RequestScopeBuilder();
    }

    private void set(RequestContext user) {
//        MDC.put("TraceId", user.traceId);
        context.set(user);
    }

    public static class RequestScopeBuilder {
        private String accessToken;
        private String traceId;
        private String requestId;
        private LocalDateTime datetime;

        RequestScopeBuilder() {
        }

        public RequestScope.RequestScopeBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public RequestScope.RequestScopeBuilder traceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        public RequestScope.RequestScopeBuilder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public RequestScope.RequestScopeBuilder datetime(LocalDateTime datetime) {
            this.datetime = datetime;
            return this;
        }


        public RequestScope build() {
            return new RequestScope(accessToken, traceId, requestId, datetime);
        }
    }

    private static class RequestContext {
        @Getter
        private String accessToken;
        @Getter
        private String traceId;
        @Getter
        private String requestId;
        @Getter
        private LocalDateTime dateTime;
//        @Getter
//        private List<LoggingEventBase> events;

        private int sequence;

        @Getter
        @Setter
        private String authorisedId;

        @Getter
        @Setter
        private int confidenceLevel;

        @Getter
        @Setter
        private String routeName;

        @Getter
        @Setter
        private Object requestBody;

        @Getter
        @Setter
        private boolean isBeta;

        private RequestContext(
                String accessToken,
                String traceId,
                String requestId,
                LocalDateTime dateTime
//                List<LoggingEventBase> events
        ) {
            this.sequence = 0;
            this.accessToken = accessToken;
            this.traceId = traceId;
            this.requestId = requestId;
//            this.events = events;
            this.dateTime = dateTime;
            this.isBeta = isBeta;
        }

        int getSequence() {
            sequence++;
            return sequence;
        }

//        void addEvent(LoggingEventBase loggingEventBase) {
//            events.add(loggingEventBase);
//        }
    }
}
