# BUILDER Stage
FROM amazoncorretto:17-al2023-jdk as builder
WORKDIR /app
COPY . .
# install maven and build the .jar file (on amazone linux image)
RUN yum install -y maven
RUN mvn package

# RUNNER Stage
# OpenJDK 17 Image based on Debian
FROM openjdk:17-bullseye as runner
# Update the package list and install tesseract-ocr
RUN apt-get update && \
    apt-get install -y tesseract-ocr && \
    rm -rf /var/lib/apt/lists/*
# Download the English language package for Tesseract
RUN mkdir -p /usr/share/tesseract-ocr/4.00/tessdata
ADD https://github.com/tesseract-ocr/tessdata_best/raw/main/eng.traineddata /usr/share/tesseract-ocr/4.00/tessdata/
# Verify Tesseract installation
RUN tesseract --version && \
    tesseract --list-langs
EXPOSE 8082
COPY --from=builder ./app/target/*.jar ocrService.jar
ENTRYPOINT ["java","-jar","ocrService.jar"]