# Bingo Game Project

## Overview

This project implements a Bingo game with both RMI (Remote Method Invocation) and RPC (Remote Procedure Call) versions. It consists of four main components:

1. **BongoGameClient:**
   - Implementation of the client that interacts with the server through the Gateway.
   - Handles player choices for playing Bingo, checking the best score, or quitting.

2. **BingoGameGateway:**
   - Implementation of the Gateway acting as an intermediary between clients and the server.

3. **BingoGameServerRMI:**
   - Implementation of the server using RMI.

4. **BingoGameServerRPC:**
   - Implementation of the server using RPC.

## How to Run

1. **BongoGameClient:**
   - Execute the `main` method to establish a socket connection with the Gateway.
   - Use the `displayMenu` method to explore game options.

2. **Part RMI:**
   - Shared Interface: `BingoService`.
   - Gateway:
     - `BingGatewayHandlerRMI`: Gateway implementation for RMI.
     - `BingoGateway`: Main class for the Gateway.
   - Server:
     - `BingoServiceImpl`: Implementation of the BingoService interface.
     - `ServerRMI`: Main class for the RMI server.

3. **Part RPC:**
   - Shared Interface: `BingoService`.
   - Gateway:
     - `BingGatewayHandlerRPC`: Gateway implementation for RPC.
     - `BingoGateway`: Main class for the Gateway.
   - Server:
     - `BingoServiceImplRPC`: Implementation of the BingoService interface.
     - `ServerRPC`: Main class for the RPC server.

## Execution

- For RMI: Uncomment relevant code and comment out RPC sections.
- For RPC: Uncomment relevant code and comment out RMI sections.

## Contributors

- [Seyfeddine Jouini](https://github.com/seyfeddine-jouini)
- [Yosra Sassi](https://github.com/yosra-sassi)

Feel free to explore and contribute!

## License

This project is licensed under the [MIT License](LICENSE).
