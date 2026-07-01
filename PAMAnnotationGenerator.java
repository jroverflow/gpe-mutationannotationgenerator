package com.biomatters.exampleSequenceAnnotationGeneratorPlugin;

import com.biomatters.geneious.publicapi.plugin.*;
import com.biomatters.geneious.publicapi.documents.AnnotatedPluginDocument;
import com.biomatters.geneious.publicapi.documents.DocumentUtilities;
import com.biomatters.geneious.publicapi.documents.MalformedURNException;
import com.biomatters.geneious.publicapi.documents.URN;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceAnnotation;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceAnnotationInterval;
import com.biomatters.geneious.publicapi.documents.sequence.NucleotideSequenceDocument;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceAlignmentDocument;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceAnnotationQualifier;

import jebl.util.ProgressListener;

import java.util.*;

/**
 * General class for finding SNPs on Manually Annotated sequences
 * 
 * @author 
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
        String refSequence = sequence.getSequenceString().toUpperCase();
        
        
        // extract annotations from sequence
        List<SequenceAnnotation> annotations = sequence.getSequenceAnnotations();
        // compiled list of mutations to return
        List<SequenceAnnotation> snpAnnotations = new ArrayList<>();

        for (SequenceAnnotation annotation : annotations) {
            String primaryMatch = annotation.getQualifierValue("Primary Match");
            if (primaryMatch != null) {
                byte[] bytes = primaryMatch.getBytes();
                StringBuilder sb = new StringBuilder();
                sb.append("length=").append(bytes.length).append(" bytes=");
            for (byte b : bytes) {
                sb.append(b).append(",");
            }
            throw new DocumentOperationException(sb.toString());
            }
        }

        /*

        for (SequenceAnnotation annotation : annotations) {
            // extract primary match (in user database)
            String primaryMatch = annotation.getQualifierValue("Primary Match");
            // not found -> continue
            if (primaryMatch == null) continue;

            // parse URN from PM qualifier value
            String urnString = null;
            int start = primaryMatch.lastIndexOf('(');
            int end = primaryMatch.lastIndexOf(')');
            if (start >= 0 && end > start) {
                urnString = primaryMatch.substring(start + 1, end).trim();
            }
            if (urnString == null || !urnString.startsWith("urn:")) continue;

            URN urn;
            try {
                urn = new URN(urnString);
            } catch (MalformedURNException e) {
                continue;
            }

            // annotation sequences to compare query against
            List<AnnotatedPluginDocument> sourceDocs =
                    DocumentUtilities.getDocumentsByURN(
                            Collections.singletonList(urn), false);
            if (sourceDocs == null || sourceDocs.isEmpty() || sourceDocs.get(0) == null) continue;

            NucleotideSequenceDocument sourceDoc =
                    (NucleotideSequenceDocument) sourceDocs.get(0).getDocument();
            String sourceSequence = sourceDoc.getSequenceString().toUpperCase();

            SequenceAnnotationInterval interval = annotation.getIntervals().get(0);
            int annotationStart = interval.getMinimumIndex();
            int annotationEnd = interval.getMaximumIndex();

            String targetSequence = refSequence.substring(annotationStart - 1, annotationEnd);

            if (sourceSequence.length() != targetSequence.length()) continue;

            List<Mutation> mutations;
            try {
                mutations = MutationDetector.detectSNPs(sourceSequence, targetSequence);
            } catch (IllegalArgumentException e) {
                continue;
            }

            for (Mutation m : mutations) {
                int absolutePosition = annotationStart + m.getPosition() - 1;
                SequenceAnnotationInterval snpInterval =
                        new SequenceAnnotationInterval(absolutePosition, absolutePosition);
                SequenceAnnotation snp = new SequenceAnnotation(
                        m.getDescription(),
                        SequenceAnnotation.TYPE_POLYMORPHISM,
                        snpInterval);
                snp.addQualifier(SequenceAnnotationQualifier.VARIANT_CHANGE,
                        m.getDescription());            // depending on type, will be either of B1>B2, Insertion(B), Deletion(B)
                snp.addQualifier(SequenceAnnotationQualifier.VARIANT_NUCLEOTIDES,
                        m.getAltBase());
                snp.addQualifier(SequenceAnnotationQualifier.VARIANT_REFERENCE_NUCLEOTIDES,
                        m.getRefBase());
                snpAnnotations.add(snp);
            }
        }

        // for method signature
        return Arrays.asList(snpAnnotations);
        */

        
    }
}
