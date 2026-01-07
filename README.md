# TeamMate – University Gaming Club Team Builder (CLI)

## Project Overview
TeamMate is a Java-based Command Line Interface (CLI) application developed to automatically form **balanced teams** for a University Gaming Club.  
The system uses participant **skills, roles, game preferences, and personality traits** to generate fair and diverse teams based on defined constraints.

This project was developed as part of the **CM2601 Coursework** and demonstrates object-oriented design, constraint-based logic, testing, logging, and version control usage.

---

## Input File – `input_participants.csv`

The application reads participant data from a CSV file located in the project root.

### CSV Format
ParticipantID,Name,PreferredGame,PreferredRole,SkillLevel,Q1,Q2,Q3,Q4,Q5

markdown
Copy code

### Field Description
- **ParticipantID** – Unique participant identifier (e.g., P001)
- **Name** – Participant name
- **PreferredGame** – Preferred game (e.g., Valorant, FIFA, CSGO)
- **PreferredRole** – Selected role (Attacker, Defender, Strategist, Supporter, Coordinator)
- **SkillLevel** – Skill rating (0–100)
- **Q1–Q5** – Personality survey responses (1–5)

Personality type is **not stored** in the CSV.  
It is calculated internally using survey responses.

---

## Personality Classification

Personality score is calculated as:
(Q1 + Q2 + Q3 + Q4 + Q5) × 4

yaml
Copy code

### Personality Types
| Score Range | Type |
|------------|------|
| 90–100 | Leader |
| 70–89 | Balanced |
| 50–69 | Thinker |

---

## Team Formation Rules

Teams are formed using the following constraints:

- **Team size:** 5 participants per team
- **Role diversity:** At least 3 different roles per team
- **Personality balance:**
    - 1 Leader
    - 1–2 Thinkers
    - Remaining Balanced
- **Game variety:** Maximum 2 players preferring the same game per team
- **Skill balance:** High-skill participants are distributed evenly
- **Fairness:** Randomized selection within constraints

The team formation logic is implemented in `TeamBuilder.java`.

---

## Output File – `formed_teams.csv`

After execution, the application generates an output CSV file.

### Output Format
TeamID,ParticipantID,Name,PreferredGame,Role,PersonalityType,SkillLevel

yaml
Copy code

Each row represents **one participant in a team**, allowing easy verification of team balance and constraint satisfaction.

---

## How to Run the Application

1. Open the project in **IntelliJ IDEA**
2. Ensure `input_participants.csv` is present in the project root
3. Run the `Main.java` file
4. Use the console menu to generate teams
5. Check the generated `formed_teams.csv` file

---

## Testing

The project includes unit tests written using **JUnit 5** for core components:

- `CSVHandlerTest` – Tests CSV input and output handling
- `PersonalityClassifierTest` – Tests personality classification logic
- `TeamBuilderTest` – Tests team creation and size constraints

Tests are located in:
src/test/java/TeamMate

yaml
Copy code

---

## Logging

Logging is configured using Java’s built-in logging framework.  
Application logs are written to:
teamate.log.0

yaml
Copy code

This supports debugging and execution tracing.

---

## Technologies Used

- Java
- Maven
- JUnit 5
- IntelliJ IDEA
- CSV File Handling
- Git & GitHub for version control

---

## Summary

This project demonstrates:
- Object-oriented programming principles
- Constraint-based team formation logic
- Clean separation of concerns
- Practical unit testing
- Logging and debugging support
- Clear documentation and version control usage

---

