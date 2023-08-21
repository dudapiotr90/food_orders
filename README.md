# Food Orders Application

This is a Dockerized application that allows users to place food orders.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Usage](#usage)
- [Roles and Endpoints](#roles-and-endpoints)
- [Customer Features](#customer-features)
- [Owner Features](#owner-features)
- [Developer Features](#developer-features)
- [API Documentation with Swagger UI](#api-documentation-with-swagger-ui)
- [APIs and Services Consumed](#apis-and-services-consumed)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## Prerequisites

- Docker: Ensure you have Docker installed on your machine. [Install Docker](https://docs.docker.com/get-docker/)
- Docker Compose: Ensure you have Docker Compose installed. [Install Docker Compose](https://docs.docker.com/compose/install/)

## Getting Started

1. Clone this repository to your local machine:

```bash
git clone https://github.com/dudapiotr90/food_orders.git
cd food-orders
``` 

## Configuration

Configure application settings in the application.yml file.
Modify the Docker Compose configuration in docker-compose.yml as needed.

## Usage

The Spring Boot backend should be accessible at http://localhost:8150.  
PostgreSQL is accessible at localhost:5432.   
MailDev web interface can be accessed at http://localhost:1080.

**Important:** The application consumes the Spoonacular REST API to enhance food ordering features. To fully utilize the Food Orders application, you need to replace the Spoonacular API key in the `application.yml` file. The provided API key in the code is deprecated due to Spoonacular's terms of use.

To update the Spoonacular API key:

1. Go to the [Spoonacular website](https://spoonacular.com/food-api) and sign up or log in.
2. Obtain a new API key from your Spoonacular account.
3. Open the `application.yml` file in the `src/main/resources` directory.
4. Replace YOUR_NEW_API_KEY_HERE with your actual Spoonacular API key:

```yaml
api:
  spoonacular:
    url: https://api.spoonacular.com/
    apiKey: YOUR_NEW_API_KEY_HERE
```

After updating the API key, build and start the Docker containers:

```bash
docker-compose up -d
```

Now, your Food Orders application should be fully functional with an up-to-date Spoonacular API key.


## Roles and Endpoints

- Customer: Accessible endpoints start with `/customer/`. Customers can search for restaurants, view menus, add food to their cart, and place orders.

- Owner: Accessible endpoints start with `/owner/`. Owners can manage their restaurants and their menu items.

- Developer: Accessible endpoints start with `/developer/`. Developers have access to additional tools and endpoints for debugging and testing.

## Customer Features

### Searching for Restaurants

As a customer, you can search for restaurants that are available in the database. Browse through the list of restaurants to find your favorite cuisines.

### Viewing Menus

Once you find a restaurant you're interested in, you can view its menu items. Explore different categories and dishes to decide what you'd like to order.

### Adding Food to Cart

Select the food items you want to order and add them to your cart. Your cart will be created automatically when you add the first item.

### Managing Your Cart

Visit your cart to review your selected items. You can edit your order, remove items, or proceed to make a purchase.

### Placing Orders

When you're satisfied with your cart, you can proceed to place your order. Your order will be processed, and you'll receive updates on its status.

### Exclusive Customer Features (with Optional spoonacular API Key)

When you provide your own spoonacular API key in the designated configuration file (`application.yml`), you'll unlock a range of exciting features designed to enhance your experience.

We're keeping the details under wraps for now, but rest assured that these features are carefully crafted to provide you with even more value and enjoyment while using our application.

## Owner Features

### Adding Restaurants

As an owner, you have the ability to add new restaurants to the platform.

### Managing Menus

Owners can manage their restaurant's menu by adding new menu positions or updating existing ones.

### Adding Delivery Addresses

Owners can add delivery addresses to each restaurant.

### Issuing Bills

After a customer places an order, as an owner, you can generate a bill for the order. This bill includes the details of the items ordered, total cost. Once the bill is issued, the customer will be notified for payment.

### Completing Orders

Once a customer has paid the bill, the owner can mark the order as completed.

## Developer Features

As a developer, you have the following responsibilities and capabilities:

### User Account Management

- Register new user accounts.
- Update account information for existing users.
- Delete inactive accounts.

### Access to Application Statistics

- View and analyze application statistics, including the number of accounts using the application.

### Database Interaction

- Search for owners and customers within the database.
- Display details of owners and customers.

### Meal Plan Generation (with spoonacular API)

- Generate meal plans for a day or a whole week using the spoonacular API.
- Requires a functional spoonacular API key provided in the `application.yml` configuration file.

## API Documentation with Swagger UI

Our application's REST API is well-documented and easily accessible through Swagger UI.  
Additionally, we provide the OpenAPI JSON. 
To access those make sure you are logged in as a developer

### Accessing Swagger UI

To access the Swagger UI and explore our API documentation: [Swagger-UI](http://localhost:8150/food-orders/developer/swagger-ui/index.html)

### OpenAPI JSON File

For developers who prefer to work with the API specification in JSON format, we provide the OpenAPI JSON file.
You can access it using the following: [OpenAPI 3 JSON](http://localhost:8150/food-orders/developer/api-docs)

## APIs and Services Consumed

For more information about the Spoonacular REST API, refer to the [Spoonacular API Documentation](https://spoonacular.com/food-api/docs).

You can also check the Spoonacular [OpenAPI 3 JSON](https://spoonacular.com/application/frontend/downloads/spoonacular-openapi-3.json) file.

## Troubleshooting

If you encounter issues with container startup or communication, ensure that you've followed the setup steps correctly and that your configurations are accurate.

## Contributing

Contributions are welcome! If you find any issues or have suggestions, please create an issue or a pull request.


Hope you enjoy using my application!


