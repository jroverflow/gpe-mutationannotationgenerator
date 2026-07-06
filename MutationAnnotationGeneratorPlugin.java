package com.biomatters.exampleSequenceAnnotationGeneratorPlugin;

import com.biomatters.geneious.publicapi.plugin.GeneiousPlugin;
import com.biomatters.geneious.publicapi.plugin.SequenceAnnotationGenerator;

/**
 * Version 0.1
 */
public class MutationAnnotationGeneratorPlugin extends GeneiousPlugin {
    public SequenceAnnotationGenerator[] getSequenceAnnotationGenerators() {
        return new SequenceAnnotationGenerator[]{
                new MutationAnnotationGenerator(),
        };
    }

    public String getName() {
        return "Mutation Annotation Generator Plugin";
    }

    public String getHelp() {
        return "Refer to the README file in the linked Github for detailed step by step instructions.";
    }

    public String getDescription() {
        return "A simple SNP annotation generator for Geneious Prime.";
        
    }

    public String getAuthors() {
        return "Janicka Lopez";
    }

    public String getVersion() {
        return "0.4";
    }

    public String getMinimumApiVersion() {
        return "4.615";
    }

    public int getMaximumApiVersion() {
        return 4;
    }
}
