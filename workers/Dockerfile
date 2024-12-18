# BUILDER Stage
FROM maven:3.9.9-amazoncorretto-17-alpine AS builder
WORKDIR /app

# Copy project files
COPY pom.xml ./
RUN mvn verify --fail-never
COPY . ./
RUN mvn package -e

# RUNNER Stage
FROM openjdk:17-jdk-slim AS runner

# Install Tesseract OCR and wget
RUN apt-get update && \
    apt-get install -y tesseract-ocr wget && \
    rm -rf /var/lib/apt/lists/*

# Create tessdata directory and download the English language data
RUN mkdir -p /usr/share/tesseract-ocr/4.00/tessdata && \
    wget -O /usr/share/tesseract-ocr/4.00/tessdata/eng.traineddata \
    https://github.com/tesseract-ocr/tessdata_best/raw/main/eng.traineddata

# Set the TESSDATA_PREFIX environment variable
ENV TESSDATA_PREFIX=/usr/share/tesseract-ocr/4.00/tessdata/

# Verify Tesseract installation and available languages
RUN tesseract --version && \
    tesseract --list-langs

# Set the working directory
WORKDIR /app

# Expose the port
EXPOSE 8082

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar ocrService.jar

# Start the worker service
ENTRYPOINT ["java", "-jar", "ocrService.jar"]
