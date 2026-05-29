package com.biomatters.exampleSequenceAnnotationGeneratorPlugin;

import com.biomatters.geneious.publicapi.plugin.*;
import com.biomatters.geneious.publicapi.documents.AnnotatedPluginDocument;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceAnnotation;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceAnnotationInterval;
import com.biomatters.geneious.publicapi.documents.sequence.NucleotideSequenceDocument;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceAlignmentDocument;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceAnnotationQualifier;

import jebl.util.ProgressListener;

import java.util.*;

/**
 * General class for finding SNPs on Manually Annotated
 * 
 * @author Janicka Lopez
 */

public class PAMAnnotationGenerator extends SequenceAnnotationGenerator {
    public GeneiousActionOptions getActionOptions() {
        return new GeneiousActionOptions("Find SNPs on Annotations",
                "Finds a SNP from a motif annotation and generates a labelled annotation").
                setMainMenuLocation(GeneiousActionOptions.MainMenu.AnnotateAndPredict);
    }

    public String getHelp() {
        return "This plugin generates SNP mutation annotations for a source and sequence.";
    }

    public Options getOptions(AnnotatedPluginDocument[] documents, 
        SelectionRange selectionRange) throws DocumentOperationException {
            // Provides all the options displayed to the user. Here none
        return null;
    }


    public DocumentSelectionSignature[] getSelectionSignatures() {
        return new DocumentSelectionSignature[] {
                // using NucleotideSequenceDocument since pulling annotations from single sequence
                new DocumentSelectionSignature(NucleotideSequenceDocument.class,1,1)
        };
    }


    // method given above method utilizes multiple sequences
    public List<List<SequenceAnnotation>>
        generateAnnotations(AnnotatedPluginDocument[] documents, 
                SequenceAnnotationGenerator.SelectionRange selectionRange,
                    ProgressListener progressListener, Options options) 
                        throws DocumentOperationException {
        
        // extract sequence doc
        NucleotideSequenceDocument sequence = 
            (NucleotideSequenceDocument)documents[0].getDocument();
        
        
        // extract annotations from sequence
        List<SequenceAnnotation> annotations = sequence.getSequenceAnnotations();

        // build string first, then throw
        StringBuilder sb = new StringBuilder();
        for (SequenceAnnotation annotation : annotations) {
            sb.append("annotation: ").append(annotation.getName())
              .append(" type: ").append(annotation.getType())
              .append("\n");
        }

        throw new DocumentOperationException(sb.toString());

        
    }
}
