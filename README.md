# Payments Project

## Overview

This project is a modular Java-based application designed to handle payment processing, orchestration, tokenization, and utility functions. It is structured into multiple modules:

- **Gateway**: Handles frontend and backend for payment processing.
- **Orchestrator**: Manages workflows and service orchestration.
- **Tokenizer**: Provides tokenization services for sensitive data.
- **Utilities**: Contains shared utility functions used across modules.

## Folder Structure

```
payments/
├── gateway/       # Payment gateway module (frontend + backend)
├── orchestrator/  # Service orchestration module
├── tokenizer/     # Tokenization module
├── utilities/     # Shared utilities module
├── pom.xml        # Parent Maven configuration
```

## Technologies Used

- **Backend**: Java, Spring Boot
- **Frontend**: React (via Vite)
- **Build Tool**: Maven
- **Database**: (Specify if applicable)
- **Other Tools**: ESLint, Vite

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd payments
   ```
2. Install dependencies for the frontend:
   ```bash
   cd gateway/frontend
   npm install
   ```
3. Build the project:
   ```bash
   cd ../../
   mvn clean install
   ```

## Build and Run Instructions

### Backend

1. Navigate to the module directory (e.g., `gateway`, `orchestrator`, etc.).
2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Frontend

1. Navigate to the `gateway/frontend` directory.
2. Start the development server:
   ```bash
   npm run dev
   ```

## Contributing

1. Fork the repository.
2. Create a new branch for your feature:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add your message"
   ```
4. Push to your branch:
   ```bash
   git push origin feature/your-feature-name
   ```
5. Create a pull request.

## License

This project is licensed under the [MIT License](LICENSE).
