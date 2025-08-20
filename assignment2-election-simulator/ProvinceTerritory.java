/**
 * ProvinceTerritory.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 2, 1
 * 
 * @author Michel Préjet
 * @version July 24th, 2025
 * 
 *          PURPOSE: Represents a province/territory entity. Contains the
 *          province/territory's full bilingual name, abbreviation, and
 *          bilingual shorthand. Provides getter methods for each of these
 *          instance variables, and a method to get a specific ProvinceTerritory
 *          object by passing in its name as a String. Also contains an array
 *          with each Canadian province/territory.
 * 
 *          NOTE: all implementation of this class except for getProvinceMatch()
 *          was provided by Simon Wermie, my instructor. The implementation of
 *          getProvinceMatch() was written by me.
 */

public class ProvinceTerritory {
        private String fullNameBilingual;
        private String abbreviation;
        private String bilingualShort;

        // Province/Territory constants
        public static final ProvinceTerritory NEWFOUNDLAND = new ProvinceTerritory(
                        "Newfoundland and Labrador/Terre-Neuve-et-Labrador", "NL", "N.L./ T.-N.-L.");
        public static final ProvinceTerritory PRINCE_EDWARD_ISLAND = new ProvinceTerritory(
                        "Prince Edward Island/Île-du-Prince-Édouard", "PE", "P.E.I./ Î.-P.-É.");
        public static final ProvinceTerritory NOVA_SCOTIA = new ProvinceTerritory("Nova Scotia/Nouvelle-Écosse", "NS",
                        "N.S./ N.-É.");
        public static final ProvinceTerritory NEW_BRUNSWICK = new ProvinceTerritory("New Brunswick/Nouveau-Brunswick",
                        "NB",
                        "N.B./ N.-B.");
        public static final ProvinceTerritory QUEBEC = new ProvinceTerritory("Quebec/Québec", "QC", "Que./ Qc");
        public static final ProvinceTerritory ONTARIO = new ProvinceTerritory("Ontario", "ON", "Ont.");
        public static final ProvinceTerritory MANITOBA = new ProvinceTerritory("Manitoba", "MB", "Man.");
        public static final ProvinceTerritory SASKATCHEWAN = new ProvinceTerritory("Saskatchewan", "SK", "Sask.");
        public static final ProvinceTerritory ALBERTA = new ProvinceTerritory("Alberta", "AB", "Alta./ Alb.");
        public static final ProvinceTerritory BRITISH_COLUMBIA = new ProvinceTerritory(
                        "British Columbia/Colombie-Britannique", "BC", "B.C./ C.-B.");
        public static final ProvinceTerritory YUKON = new ProvinceTerritory("Yukon", "YT", "Y.T./ Yn");
        public static final ProvinceTerritory NORTHWEST_TERRITORIES = new ProvinceTerritory(
                        "Northwest Territories/Territoires du Nord-Ouest", "NT", "N.W.T./ T.N.-O.");
        public static final ProvinceTerritory NUNAVUT = new ProvinceTerritory("Nunavut", "NU", "Nun./ Nt");

        // Array of all provinces
        public static final ProvinceTerritory[] ALL_PROVINCES = {
                        NEWFOUNDLAND, PRINCE_EDWARD_ISLAND, NOVA_SCOTIA, NEW_BRUNSWICK,
                        QUEBEC, ONTARIO, MANITOBA, SASKATCHEWAN, ALBERTA,
                        BRITISH_COLUMBIA, YUKON, NORTHWEST_TERRITORIES, NUNAVUT
        };

        private ProvinceTerritory(String fullNameBilingual, String abbreviation, String bilingualShort) {
                this.fullNameBilingual = fullNameBilingual;
                this.abbreviation = abbreviation;
                this.bilingualShort = bilingualShort;
        }

        public String getFullNameBilingual() {
                return fullNameBilingual;
        }

        public String getAbbreviation() {
                return abbreviation;
        }

        public String getBilingualShort() {
                return bilingualShort;
        }

        @Override
        public String toString() {
                return fullNameBilingual + " (" + bilingualShort + ")";
        }

        /**
         * Compares an input string to each ProvinceTerritory's full bilingual
         * name, abbreviation, and bilingual shorthand, and returns the correct
         * ProvinceTerritory if any match is found. Throws an IllegalArgumentException
         * if the input is null, empty, or only whitespace.
         * 
         * @param input the string to compare to each ProvinceTerritory's
         *              identifiers.
         * @return the ProvinceTerritory corresponding to input; or null if input
         *         is invalid or no matches are found.
         */
        public static ProvinceTerritory getProvinceMatch(String input) {
                // Perform validation checks.
                IOHelper.validateString(input, "Province/Territory identifier");

                // Compare input with fullNameBilingual, abbreviation, and
                // bilingualShort for each province/territory.
                input = input.trim();
                ProvinceTerritory returnVal = null;
                for (ProvinceTerritory i : ALL_PROVINCES) {
                        if (i.getFullNameBilingual().equals(input)
                                        || i.getAbbreviation().equals(input)
                                        || i.getBilingualShort().equals(input)) {
                                returnVal = i;
                        }
                }

                return returnVal;
        }
}
