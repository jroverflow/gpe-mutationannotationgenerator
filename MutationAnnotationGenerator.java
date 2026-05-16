package com.biomatters.exampleSequenceAnnotationGeneratorPlugin;

import com.biomatters.geneious.publicapi.plugin.*;
import com.biomatters.geneious.publicapi.utilities.SequenceUtilities;
import com.biomatters.exampleSequenceAnnotationGeneratorPlugin.MutationDetector;
import com.biomatters.geneious.publicapi.documents.AnnotatedPluginDocument;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceAnnotation;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceAnnotationInterval;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceAlignmentDocument;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceAnnotationQualifier;

import jebl.util.ProgressListener;

import java.util.*;

/**
 * General class for MutationAnnotationGenerator
 * 
 * @author Janicka Lopez
 */

public class MutationAnnotationGenerator extends SequenceAnnotationGenerator {
    public GeneiousActionOptions getActionOptions() {
        return new GeneiousActionOptions("Find SNPs",
                "Finds a SNP and generates a labelled annotation").
                setMainMenuLocation(GeneiousActionOptions.MainMenu.AnnotateAndPredict);
    }

    public String getHelp() {
        return "This plugin generates SNP mutation annotations for a source and sequence.";
    }

    public Options getOptions(AnnotatedPluginDocument[] documents, 
        SelectionRange selectionRange) throws DocumentOperationException {
            // Provides all the options displayed to the user. 
        return null;
    }


    public DocumentSelectionSignature[] getSelectionSignatures() {
        return new DocumentSelectionSignature[] {
                // using SequenceAlignmentDocument instead of NucleotideSequenceDocument because of multiple sequences used
                new DocumentSelectionSignature(SequenceAlignmentDocument.class,1,1)
        };
    }


    // method given above method utilizes multiple sequences
    public List<List<SequenceAnnotation>>
        generateAnnotations(AnnotatedPluginDocument[] documents, 
                SequenceAnnotationGenerator.SelectionRange selectionRange,
                    ProgressListener progressListener, Options options) 
                        throws DocumentOperationException {
        
        // extract alignment doc (eg. primary alignment)
        SequenceAlignmentDocument alignment = (SequenceAlignmentDocument)documents[0].getDocument();

        // extract ref and sequenced files
        String refAligned = 
            alignment.getSequence(0).getSequenceString();
        String queryAligned = 
            alignment.getSequence(1).getSequenceString();

        // Checking reverse strand
        String queryReversed = SequenceUtilities.reverseComplement(queryAligned).toString();

        // utilize MutationDetector.java logic for compiled list
        // added reverse mutations in a separate list
        List<Mutation> forwardMutations = MutationDetector.detectSNPs(refAligned, queryAligned);
        List<Mutation> reverseMutations = MutationDetector.detectSNPs(refAligned, queryReversed);

        // convert to geneious SequenceAnnotation type
        // later utilize TYPE_EDITING_HISTORY... for further logic, for now will utilize TYPE_POLYMORPHISM
        List<SequenceAnnotation> mutationAnnotations = new ArrayList<>();
        
        // forward strand pass
        for (Mutation m: forwardMutations) {
            SequenceAnnotationInterval position = new SequenceAnnotationInterval(
                m.getPosition(), 
                m.getPosition());
            SequenceAnnotation annotation = new SequenceAnnotation(
                m.getDescription(),         // of format "Base1>Base2"
                SequenceAnnotation.TYPE_POLYMORPHISM,          // Indicates polymorphism 
                position);                  // Indicates position
            
            // optional qualifiers added
            annotation.addQualifier(SequenceAnnotationQualifier.VARIANT_CHANGE, 
                m.getDescription());
            annotation.addQualifier(SequenceAnnotationQualifier.VARIANT_NUCLEOTIDES, 
                m.getAltBase());
            annotation.addQualifier(SequenceAnnotationQualifier.VARIANT_REFERENCE_NUCLEOTIDES, 
                m.getRefBase());
            
            mutationAnnotations.add(annotation);
        }

        // reverse strand pass
        for (Mutation m: reverseMutations) {
            int revPosition = refAligned.length() - m.getPosition() + 1;
            SequenceAnnotationInterval position = new SequenceAnnotationInterval(
                revPosition + 1, 
                revPosition);
            SequenceAnnotation annotation = new SequenceAnnotation(
                m.getDescription(),         // of format "Base1>Base2"
                SequenceAnnotation.TYPE_POLYMORPHISM,          // Indicates polymorphism 
                position);                  // Indicates position
            
            // optional qualifiers added
            annotation.addQualifier(SequenceAnnotationQualifier.VARIANT_CHANGE, 
                m.getDescription());
            annotation.addQualifier(SequenceAnnotationQualifier.VARIANT_NUCLEOTIDES, 
                m.getAltBase());
            annotation.addQualifier(SequenceAnnotationQualifier.VARIANT_REFERENCE_NUCLEOTIDES, 
                m.getRefBase());
            
            mutationAnnotations.add(annotation);

        }

        // method given two sequences, must return two sequence annotation lists
        List<SequenceAnnotation> emptyList = new ArrayList<>();

        return Arrays.asList(emptyList, mutationAnnotations);
    }
}
