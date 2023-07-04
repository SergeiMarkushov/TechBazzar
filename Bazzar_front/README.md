## 🚀 Getting Started

### Prerequisites

- [Node.js](https://nodejs.org/en/) >= 14.x
- [npm](https://www.npmjs.com/) >= 6.x
- [Docker](https://www.docker.com/products/docker-desktop) (if using Docker)

### Installation and Running

#### 🖥️ Option 1: Using npm

1. Clone the repository:

   ```sh
   git clone https://github.com/SergeiMarkushov/TechBazzar.git
   ```

2. Navigate to the project directory:

   ```sh
   cd Bazzar_front
   ```

3. Install dependencies:

   ```sh
   npm install
   ```

4. Start the development server:

   ```sh
   npm start
   ```

5. Open your browser and visit `http://localhost:3000`.

#### 🐳 Option 2: Using Docker

1. Clone the repository:

   ```sh
   git clone https://github.com/SergeiMarkushov/TechBazzar.git
   ```

2. Navigate to the project directory:

   ```sh
   cd Bazzar_front
   ```

3. Build the Docker image:

   ```sh
   docker build -t ps-container:dev .
   ```

4. Run the Docker container:

   ```sh
   docker run -it --rm -v ${PWD}:/app -v /app/node_modules -p 3000:3000 -e CHOKIDAR_USEPOLLING=true ps-container:dev
   ```

5. Open your browser and visit `http://localhost:3000`.