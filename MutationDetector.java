package com.biomatters.exampleSequenceAnnotationGeneratorPlugin;


import java.util.ArrayList;
import java.util.List;

public class MutationDetector {

    /**
     * Detects SNP, and compiles it into a list of Mutation objects
     * @param refAligned Reference Sequence
     * @param queryAligned Input Sequence to test
     */
    public static List<Mutation> detectSNPs(String refAligned, String queryAligned) {
        List<Mutation> mutations = new ArrayList<>();

        if (refAligned.length() != queryAligned.length()) {
            throw new IllegalArgumentException("Aligned sequences must have the same length");
        }

        for (int i = 0; i < refAligned.length(); i++) {
            char r = refAligned.charAt(i);
            char q = queryAligned.charAt(i);

            // duplicate gaps okay
            if (r == '-' && q == '-') continue;
            // change in query
            if (r != q) {
                mutations.add(new Mutation(i + 1, String.valueOf(r), String.valueOf(q)));
            }
        }

        // compiled list of mutations
        return mutations;
    }
}