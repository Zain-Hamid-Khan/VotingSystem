/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author MULTI LAPTOP
 */
package evote;

import java.io.*;
import java.util.*;

class Admin implements Serializable {
    private String name;
    private String email;
    private String password;
    private String ELECTION_DATA_FILE_PATH;

    public Admin(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void saveAdminData(String DATA_FILE_PATH) {
        try (PrintWriter writer = new PrintWriter(DATA_FILE_PATH)) {
            writer.println(name);
            writer.println(email);
            writer.println(password);
            System.out.println("Admin data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving admin data: " + e.getMessage());
        }
    }

    public static Admin loadAdminData(String DATA_FILE_PATH) {
        try (Scanner scanner = new Scanner(new File(DATA_FILE_PATH))) {
            String name = scanner.nextLine();
            String email = scanner.nextLine();
            String password = scanner.nextLine();
            return new Admin(name, email, password);
        } catch (IOException e) {
            System.out.println("Error loading admin data: " + e.getMessage());
        }
        return null;
    }
    
    public void viewResults() {
        Election election = Election.loadElectionData(ELECTION_DATA_FILE_PATH);
        if (election != null) {
            List<Candidate> candidates = election.getCandidates();
            if (!candidates.isEmpty()) {
                System.out.println("\n==== Results ====");
                for (Candidate candidate : candidates) {
                    System.out.println("Name: " + candidate.getName());
                    System.out.println("Party: " + candidate.getParty());
                    System.out.println("Vote Count: " + candidate.getVoteCount());
                    System.out.println();
                }
            } else {
                System.out.println("No candidates found.");
            }
        } else {
            System.out.println("No election exists.");
        }
    }

    public void declareResults() {
        Election election = Election.loadElectionData(ELECTION_DATA_FILE_PATH);
        if (election != null) {
            List<Candidate> candidates = election.getCandidates();
            if (!candidates.isEmpty()) {
                Candidate winner = null;
                int maxVoteCount = 0;
                boolean tie = false;

                for (Candidate candidate : candidates) {
                    int voteCount = candidate.getVoteCount();
                    if (voteCount > maxVoteCount) {
                        maxVoteCount = voteCount;
                        winner = candidate;
                        tie = false;
                    } else if (voteCount == maxVoteCount) {
                        tie = true;
                    }
                }

                if (tie) {
                    System.out.println("The election resulted in a tie.");
                } else if (winner != null) {
                    System.out.println("The winner of the election is:");
                    System.out.println("Name: " + winner.getName());
                    System.out.println("Party: " + winner.getParty());
                    System.out.println("Vote Count: " + winner.getVoteCount());
                } else {
                    System.out.println("No candidates found.");
                }
            } else {
                System.out.println("No candidates found.");
            }
        } else {
            System.out.println("No election exists.");
        }
    }
}

class Voter implements Serializable {
    private String id;
    private String name;
    private String email;
    private String password;
    private boolean hasVoted;

    public Voter(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.hasVoted = false;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    public static void saveVoterData(List<Voter> voters, String DATA_FILE_PATH) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE_PATH))) {
            outputStream.writeObject(voters);
            System.out.println("Voter data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving voter data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Voter> loadVoterData(String DATA_FILE_PATH) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DATA_FILE_PATH))) {
            return (List<Voter>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading voter data: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}

class Candidate implements Serializable {
    private String id;
    private String name;
    private String party;
    private int voteCount;

    public Candidate(String id, String name, String party) {
        this.id = id;
        this.name = name;
        this.party = party;
        this.voteCount = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParty() {
        return party;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void incrementVoteCount() {
        voteCount++;
    }

    public static void saveCandidateData(List<Candidate> candidates, String DATA_FILE_PATH) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE_PATH))) {
            outputStream.writeObject(candidates);
            System.out.println("Candidate data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving candidate data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Candidate> loadCandidateData(String DATA_FILE_PATH) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DATA_FILE_PATH))) {
            return (List<Candidate>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading candidate data: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}

class Election implements Serializable {
    private String title;
    private List<Candidate> candidates;

    public Election(String title) {
        this.title = title;
        this.candidates = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
    }

    public void removeCandidate(Candidate candidate) {
        candidates.remove(candidate);
    }

    public Candidate getCandidateById(String id) {
        for (Candidate candidate : candidates) {
            if (candidate.getId().equals(id)) {
                return candidate;
            }
        }
        return null;
    }

    public void saveElectionData(String DATA_FILE_PATH) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE_PATH))) {
            outputStream.writeObject(this);
            System.out.println("Election data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving election data: " + e.getMessage());
        }
    }

    public static Election loadElectionData(String DATA_FILE_PATH) {
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DATA_FILE_PATH))) {
        Object data = inputStream.readObject();
        if (data instanceof Election) {
            return (Election) data;
        } else if (data instanceof ArrayList) {
            ArrayList<Candidate> candidates = (ArrayList<Candidate>) data;
            Election election = new Election("");
            election.setCandidates(candidates);
            return election;
        }
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error loading election data: " + e.getMessage());
    }
    return null;
}


    public void setCandidates(ArrayList<Candidate> candidates) {
    this.candidates = candidates;
}
}


class VotingSystem {
    private static final String ADMIN_DATA_FILE_PATH = "admin_data.txt";
    private static final String VOTER_DATA_FILE_PATH = "voter_data.txt";
    private static final String ELECTION_DATA_FILE_PATH = "election_data.txt";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        displayMainMenu();
    }

    private static void displayMainMenu() {
        while (true) {
            System.out.println("==== Welcome to eVoting System ====");
            System.out.println("1. Admin Signup");
            System.out.println("2. Voter Signup");
            System.out.println("3. Admin Login");
            System.out.println("4. Voter Login");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    signupAdmin();
                    break;
                case 2:
                    signupVoter();
                    break;
                case 3:
                    if (loginAdmin()) {
                        adminDashboard();
                    }
                    break;
                case 4:
                    if (loginVoter()) {
                        voterDashboard();
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static boolean loginAdmin() {
        Admin admin = Admin.loadAdminData(ADMIN_DATA_FILE_PATH);
        if (admin != null) {
            System.out.print("Enter admin email: ");
            String enteredEmail = scanner.nextLine();
            System.out.print("Enter admin password: ");
            String enteredPassword = scanner.nextLine();
            if (admin.getEmail().equals(enteredEmail) && admin.getPassword().equals(enteredPassword)) {
                System.out.println("Admin login successful.");
                return true;
            }
        }
        System.out.println("Invalid admin credentials. Please try again.");
        return false;
    }

    private static boolean loginVoter() {
        List<Voter> voters = Voter.loadVoterData(VOTER_DATA_FILE_PATH);
        if (!voters.isEmpty()) {
            System.out.print("Enter voter email: ");
            String enteredEmail = scanner.nextLine();
            System.out.print("Enter voter password: ");
            String enteredPassword = scanner.nextLine();
            for (Voter voter : voters) {
                if (voter.getEmail().equals(enteredEmail) && voter.getPassword().equals(enteredPassword)) {
                    System.out.println("Voter login successful.");
                    return true;
                }
            }
        }
        System.out.println("Invalid voter credentials. Please try again.");
        return false;
    }

    private static void signupAdmin() {
        System.out.print("Enter admin name: ");
        String name = scanner.nextLine();
        System.out.print("Enter admin email: ");
        String email = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        Admin admin = new Admin(name, email, password);
        admin.saveAdminData(ADMIN_DATA_FILE_PATH);
        System.out.println("Admin signup successful.");
    }

    private static void signupVoter() {
        System.out.print("Enter voter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter voter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter voter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter voter password: ");
        String password = scanner.nextLine();

        List<Voter> voters = Voter.loadVoterData(VOTER_DATA_FILE_PATH);
        voters.add(new Voter(id, name, email, password));
        Voter.saveVoterData(voters, VOTER_DATA_FILE_PATH);
        System.out.println("Voter signup successful.");
    }

    private static void adminDashboard() {
        while (true) {
            System.out.println("\n==== Admin Dashboard ====");
            System.out.println("1. Create Election");
            System.out.println("2. Add Candidate");
            System.out.println("3. Remove Candidate");
            System.out.println("4. View Results");
            System.out.println("5. Declare Results");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    createElection();
                    break;
                case 2:
                    addCandidate();
                    break;
                case 3:
                    removeCandidate();
                    break;
                case 4:
                    admin.viewResults();
                    break;
                case 5:
                    admin.declareResults();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createElection() {
        System.out.print("Enter election title: ");
        String title = scanner.nextLine();

        Election election = new Election(title);
        election.saveElectionData(ELECTION_DATA_FILE_PATH);
        System.out.println("Election created successfully.");
    }

    private static void addCandidate() {
        Election election = Election.loadElectionData(ELECTION_DATA_FILE_PATH);
        if (election != null) {
            System.out.print("Enter candidate ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter candidate name: ");
            String name = scanner.nextLine();
            System.out.print("Enter candidate party: ");
            String party = scanner.nextLine();

            Candidate candidate = new Candidate(id, name, party);
            election.addCandidate(candidate);
            election.saveElectionData(ELECTION_DATA_FILE_PATH);
            System.out.println("Candidate added successfully.");
        } else {
            System.out.println("No election exists. Please create an election first.");
        }
    }

    private static void removeCandidate() {
        Election election = Election.loadElectionData(ELECTION_DATA_FILE_PATH);
        if (election != null) {
            System.out.print("Enter candidate ID to remove: ");
            String id = scanner.nextLine();

            Candidate candidate = election.getCandidateById(id);
            if (candidate != null) {
                election.removeCandidate(candidate);
                election.saveElectionData(ELECTION_DATA_FILE_PATH);
                System.out.println("Candidate removed successfully.");
            } else {
                System.out.println("Candidate with ID " + id + " does not exist.");
            }
        } else {
            System.out.println("No election exists. Please create an election first.");
        }
    }

    private static void voterDashboard() {
        while (true) {
            System.out.println("\n==== Voter Dashboard ====");
            System.out.println("1. View Candidates");
            System.out.println("2. Vote");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    viewCandidates();
                    break;
                case 2:
                    vote();
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewCandidates() {
        Election election = Election.loadElectionData(ELECTION_DATA_FILE_PATH);
        if (election != null) {
            List<Candidate> candidates = election.getCandidates();
            if (!candidates.isEmpty()) {
                System.out.println("\n==== Candidates ====");
                for (Candidate candidate : candidates) {
                    System.out.println("ID: " + candidate.getId());
                    System.out.println("Name: " + candidate.getName());
                    System.out.println("Party: " + candidate.getParty());
                    System.out.println("Vote Count: " + candidate.getVoteCount());
                    System.out.println();
                }
            } else {
                System.out.println("No candidates found.");
            }
        } else {
            System.out.println("No election exists.");
        }
    }

    private static void vote() {
        Election election = Election.loadElectionData(ELECTION_DATA_FILE_PATH);
        if (election != null) {
            List<Candidate> candidates = election.getCandidates();
            if (!candidates.isEmpty()) {
                System.out.print("Enter candidate ID to vote: ");
                String id = scanner.nextLine();

                Candidate candidate = election.getCandidateById(id);
                if (candidate != null) {
                    List<Voter> voters = Voter.loadVoterData(VOTER_DATA_FILE_PATH);
                    System.out.print("Enter your voter ID: ");
                    String voterId = scanner.nextLine();

                    for (Voter voter : voters) {
                        if (voter.getId().equals(voterId)) {
                            if (voter.hasVoted()) {
                                System.out.println("You have already voted.");
                            } else {
                                candidate.incrementVoteCount();
                                voter.setHasVoted(true);
                                Candidate.saveCandidateData(candidates, ELECTION_DATA_FILE_PATH);
                                Voter.saveVoterData(voters, VOTER_DATA_FILE_PATH);
                                System.out.println("Vote casted successfully.");
                            }
                            return;
                        }
                    }
                    System.out.println("Voter with ID " + voterId + " not found.");
                } else {
                    System.out.println("Candidate with ID " + id + " not found.");
                }
            } else {
                System.out.println("No candidates found.");
            }
        } else {
            System.out.println("No election exists.");
        }
    }
    class admin implements Serializable {
    private static final String ELECTION_DATA_FILE_PATH = "election_data.txt";

    public static void viewResults() {
        Election election = Election.loadElectionData(ELECTION_DATA_FILE_PATH);
        if (election != null) {
            List<Candidate> candidates = election.getCandidates();
            if (!candidates.isEmpty()) {
                try {
                    FileWriter writer = new FileWriter("results.txt");

                    writer.write("==== Results ====\n");
                    for (Candidate candidate : candidates) {
                        writer.write("Name: " + candidate.getName() + "\n");
                        writer.write("Party: " + candidate.getParty() + "\n");
                        writer.write("Vote Count: " + candidate.getVoteCount() + "\n\n");
                    }

                    writer.close();
                    System.out.println("Results saved to results.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No candidates found.");
            }
        } else {
            System.out.println("No election exists.");
        }
    }

    public static void declareResults() {
        Election election = Election.loadElectionData(ELECTION_DATA_FILE_PATH);
        if (election != null) {
            List<Candidate> candidates = election.getCandidates();
            if (!candidates.isEmpty()) {
                Candidate winner = null;
                int maxVoteCount = 0;
                boolean tie = false;

                for (Candidate candidate : candidates) {
                    int voteCount = candidate.getVoteCount();
                    if (voteCount > maxVoteCount) {
                        maxVoteCount = voteCount;
                        winner = candidate;
                        tie = false;
                    } else if (voteCount == maxVoteCount) {
                        tie = true;
                    }
                }

                try {
                    FileWriter writer = new FileWriter("results.txt");

                    if (tie) {
                        writer.write("The election resulted in a tie.\n");
                    } else if (winner != null) {
                        writer.write("The winner of the election is:\n");
                        writer.write("Name: " + winner.getName() + "\n");
                        writer.write("Party: " + winner.getParty() + "\n");
                        writer.write("Vote Count: " + winner.getVoteCount() + "\n");
                    } else {
                        writer.write("No candidates found.\n");
                    }

                    writer.close();
                    System.out.println("Results saved to results.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No candidates found.");
            }
        } else {
            System.out.println("No election exists.");
        }
    }
}
}