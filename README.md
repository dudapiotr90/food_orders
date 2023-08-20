# Food Orders Application

This is a Dockerized application that allows users to place food orders.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Usage](#usage)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

## Prerequisites

- Docker: Ensure you have Docker installed on your machine. [Install Docker](https://docs.docker.com/get-docker/)
- Docker Compose: Ensure you have Docker Compose installed. [Install Docker Compose](https://docs.docker.com/compose/install/)

## Getting Started

1. Clone this repository to your local machine:

```bash
git clone https://github.com/dudapiotr90/food_orders.git
cd food-orders
``` 

##Configuration

Configure application settings in the application.yml file.
Modify the Docker Compose configuration in docker-compose.yml as needed.

##Usage

The Spring Boot backend should be accessible at http://localhost:8150.  
PostgreSQL is accessible at localhost:5432.   
MailDev web interface can be accessed at http://localhost:1080.

**Important:** The application consumes the Spoonacular REST API to enhance food ordering features. To fully utilize the Food Orders application, you need to replace the Spoonacular API key in the `application.yml` file. The provided API key in the code is deprecated due to Spoonacular's terms of use.

To update the Spoonacular API key:

1. Go to the [Spoonacular website](https://spoonacular.com/food-api) and sign up or log in.
2. Obtain a new API key from your Spoonacular account.
3. Open the `application.yml` file in the `src/main/resources` directory.
4. Replace the deprecated API key with your new API key:

```yaml
api:
  spoonacular:
    url: https://api.spoonacular.com/
    apiKey: YOUR_NEW_API_KEY_HERE
```

Replace YOUR_NEW_API_KEY_HERE with your actual Spoonacular API key.

After updating the API key, rebuild and start the Docker containers:

```bash
docker-compose up -d
```

Now, your Food Orders application should be fully functional with an up-to-date Spoonacular API key.

For more information about the Spoonacular REST API, refer to the [Spoonacular API Documentation](https://spoonacular.com/food-api/docs).

You can also check the Spoonacular [OpenAPI 3 JSON](https://spoonacular.com/application/frontend/downloads/spoonacular-openapi-3.json) file.

##Troubleshooting

If you encounter issues with container startup or communication, ensure that you've followed the setup steps correctly and that your configurations are accurate.

##Contributing

Contributions are welcome! If you find any issues or have suggestions, please create an issue or a pull request.

