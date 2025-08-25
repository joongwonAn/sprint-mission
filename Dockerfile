# 빌드 단계
FROM amazoncorretto:17 AS builder
WORKDIR /app

# 1) 변경 빈도 낮은 Gradle 관련 파일 먼저 복사(캐시 활용)
COPY gradlew gradlew
COPY gradle gradle
COPY settings.gradle settings.gradle
COPY build.gradle build.gradle
RUN ./gradlew --no-daemon build -x test || true \
 && ./gradlew --no-daemon dependencies || true

# 환경변수 설정
ENV PROJECT_NAME=discodeit
ENV PROJECT_VERSION=1.2-M8
ENV JAVA_OPTS=""

# 2) 소스는 코드 복사
COPY src ./src
# Gradle Wrapper로 빌드(bootJar)
RUN ./gradlew --no-daemon clean bootJar -x test

# 실행 단계: 경량 런타임만 포함
FROM amazoncorretto:17
WORKDIR /app
# 빌드 산출물 복사
COPY --from=builder /app/build/libs/${PROJECT_NAME}-${PROJECT_VERSION}.jar app.jar
EXPOSE 80
# 애플리케이션 실행
ENTRYPOINT ["sh", "-lc", "exec java $JAVA_OPTS -jar app.jar"]