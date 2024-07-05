# Electronic Voting System

This Java application implements an electronic voting system that allows administrators to manage elections, candidates, and results, and voters to register, login, view candidates, and cast votes.

## Features

### Admin Features:
- Signup and login.
- Create and manage elections.
- Add and remove candidates.
- View election results.
- Declare election winners or ties.

### Voter Features:
- Signup and login.
- View candidates participating in elections.
- Cast votes for candidates.

## Core Classes

- **Admin**: Manages admin details, election creation, candidate management, and result declaration.
- **Voter**: Stores voter information, manages voter registration and login, and handles voting status.
- **Candidate**: Represents candidates participating in elections, tracks vote counts.
- **Election**: Stores election title and candidate information, supports election creation, candidate management, and result storage.
- **VotingSystem**: Main class handling user interaction through command-line interface (CLI), manages main menu, admin, and voter operations.

## Data Persistence

Uses file handling and serialization to persist admin, voter, candidate, and election data between program runs.

