FROM mingc/android-build-box:latest

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew assembleDebug

FROM alpine:latest

COPY --from=0 /app/app/build/outputs/apk/debug/app-debug.apk /app.apk

CMD ["sh"]