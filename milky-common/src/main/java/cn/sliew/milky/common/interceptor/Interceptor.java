package cn.sliew.milky.common.interceptor;

import cn.sliew.milky.common.filter.ActionFilter;

import java.io.Serializable;

/**
 * The Interceptor allows to intercept the invocation.
 * <p>
 * This is useful to provide logging, fallback, exception, transform or substitute values.
 * <p>
 *
 * @see ActionFilter#order()
 */
public interface Interceptor<Request, Response> extends Serializable {

    /**
     * Intercept the invocation and return the corresponding response or a custom response built by the interceptor.
     * Calling {@link InterceptorContext#proceed(java.lang.Object)} will continue to execute the interceptor chain.
     * The chain can be short-circuited by returning another instance of response
     *
     * @param request the request being intercepted.
     * @param context the interceptor context.
     */
    Response intercept(Request request, InterceptorContext<Request, Response> context);

    Interceptor EMPTY = new Interceptor() {
        private static final long serialVersionUID = -9169967347679239961L;

        @Override
        public Object intercept(Object o, InterceptorContext context) {
            return null;
        }
    };

}
