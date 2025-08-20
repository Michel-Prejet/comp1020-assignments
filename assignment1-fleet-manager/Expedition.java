
/**
 * Expedition.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 1, 3
 * 
 * @author Michel Préjet
 * @version July 14th, 2025
 * 
 *          PURPOSE: Represents an Expedition, including its unique ID, the 
 *          starship carrying it out, and its outcome. Assigns random outcomes 
 *          to expeditions which include discovering planets, recovering 
 *          artifacts, and entering combat.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

import java.util.Random;

public class Expedition {

    private int expId;
    private Starship ship;
    private String outcome;
    private final int MAX_OUTCOME_INDEX = 3;
    private static int nextExpId = 1;
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 9;
    private static final String[] TREASURE_STRINGS = {
            "The %s",
            "The lost treasure of %s",
            "The forgotten relic of %s",
            "%s's ancient secrets",
            "%s's cursed hoard",
            "An encrypted cache of %s",
    };

    /**
     * Constructs a new expedition carried out by a given starship. Assigns a
     * unique ID to the expedition and selects one of five random outcomes:
     * no significant events, discovering a planet, discovering an artifact,
     * getting damaged in combat, or entering combat without suffering damage.
     * Stores any artifact discovered in one of the starship's StorageModules
     * (if one exists).
     * 
     * @param ship   the starship undertaking the expedition.
     * @param random random object used to select random outcomes.
     */
    public Expedition(Starship ship, Random random) {
        this.expId = nextExpId++;
        this.ship = ship;

        // Select a random outcome to assign to the Expedition.
        int outcomeId = random.nextInt(MAX_OUTCOME_INDEX + 1);

        // Execute the random outcome selected.
        String itemName = "";
        if (outcomeId == 0) {
            // No significant events.
            this.outcome = "Experienced no significant events.";
        } else if (outcomeId == 1) {
            // Discovered planet.
            this.ship.discoveredPlanet();
            this.outcome = "Discovered a new planet named: " + generateName(random) + ".";
        } else if (outcomeId == 2) {
            // Found artifact.
            int templateIndex = random.nextInt(TREASURE_STRINGS.length);
            itemName = generateName(random);
            String formattedName = String.format(TREASURE_STRINGS[templateIndex], itemName);
            this.outcome = "Recovered an artifact identified as: " + formattedName;
        } else if (outcomeId == 3) {
            // Engaged in combat.
            boolean damaged = random.nextBoolean();
            if (damaged) {
                // Damaged in combat.
                this.outcome = String.format("Entered combat with an enemy vessel: Module %s was damaged.",
                        this.ship.combatLost(random));
            } else {
                // Not damaged in combat.
                this.outcome = String.format("Entered combat with an enemy vessel: No modules damaged.");
            }

        }

        // Complete expedition for ship.
        if (itemName.equals("")) {
            this.ship.completeExpedition(random, null);
        } else {
            this.ship.completeExpedition(random, itemName);
        }
    }

    /**
     * Returns a string representation of the current expedition object,
     * including its ID, the Starship assigned to it, and its outcome.
     * 
     * @return a string representation of the current expedition object.
     */
    public String toString() {
        return String.format("Expedition #%d: [%s] - %s", this.expId, this.ship.getName(), this.outcome);
    }

    /**
     * Generates a random name of length 3 to 12 which starts with an
     * uppercase letter and alternates between consonants and vowels.
     * 
     * @param random the random object used to select random letters.
     * @return the randomly generated name.
     */
    public static String generateName(Random random) {
        String randName = "";
        String[] vowels = { "a", "e", "i", "o", "u" };
        String[] consonants = { "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "v",
                "w", "x", "z" };

        int nameLength = random.nextInt(MAX_NAME_LENGTH + 1) + MIN_NAME_LENGTH;

        // Generate first letter.
        boolean firstVowel;
        if (random.nextBoolean()) {
            randName += vowels[random.nextInt(vowels.length)].toUpperCase();
            firstVowel = true;
        } else {
            randName += consonants[random.nextInt(consonants.length)].toUpperCase();
            firstVowel = false;
        }

        // Generate the rest of the letters.
        if (firstVowel) {
            for (int i = 0; i < nameLength - 1; i++) {
                if (i % 2 == 0) {
                    randName += consonants[random.nextInt(consonants.length)];
                } else {
                    randName += vowels[random.nextInt(vowels.length)];
                }
            }
        } else {
            for (int i = 0; i < nameLength - 1; i++) {
                if (i % 2 != 0) {
                    randName += consonants[random.nextInt(consonants.length)];
                } else {
                    randName += vowels[random.nextInt(vowels.length)];
                }
            }
        }

        return randName;
    }
}
