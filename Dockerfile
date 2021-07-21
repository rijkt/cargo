FROM theasp/clojurescript-nodejs:alpine as builder
RUN mkdir /build/
WORKDIR /build/
COPY . /build/
RUN lein deps
RUN lein fig:min

FROM nginx:stable as prod
RUN rm -rf /usr/share/nginx/html/*
COPY --from=builder /build/resources/public/ /usr/share/nginx/html/
COPY --from=builder /build/default.conf.template /etc/nginx/conf.d/default.conf.template
# Run the app.  CMD is required to run on Heroku
# $PORT is set by Heroku
CMD /bin/bash -c "envsubst '\$PORT' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf" && nginx -g 'daemon off;'

