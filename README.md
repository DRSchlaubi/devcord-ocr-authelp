# OCR Server
This is a small ktor server that allows reading text on images using [GCP Vision](https://cloud.google.com/vision)

## Usage
To use the API simply make a post request to the `/ocr` endpoint containing the image to read. The API will return the text as response.

# Environment
- `MAX_REQUESTS` the maximal amount of requests per month, once reached every request will return `429 Too Many Requests`
- `LOG_LEVEL` the `org.slf4j.event.Level` used for logging
- `TOKENS` a comma-separated list of valid tokens

# Deployment
 - Download [Docker Compose](https://docs.docker.com/compose/)
 - Run `docker-compose up -d`