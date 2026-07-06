package com.biomatters.exampleSequenceAnnotationGeneratorPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.biomatters.geneious.publicapi.documents.AnnotatedPluginDocument;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceAlignmentDocument;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceAnnotation;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceAnnotationInterval;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceAnnotationQualifier;
import com.biomatters.geneious.publicapi.plugin.DocumentOperationException;
import com.biomatters.geneious.publicapi.plugin.DocumentSelectionSignature;
import com.biomatters.geneious.publicapi.plugin.GeneiousActionOptions;
import com.biomatters.geneious.publicapi.plugin.Options;
import com.biomatters.geneious.publicapi.plugin.SequenceAnnotationGenerator;

import jebl.util.ProgressListener;

/**
 * General class for MutationAnnotationGenerator
 * @author 
 */
public class MutationAnnotationGenerator extends SequenceAnnotationGenerator {

    public GeneiousActionOptions getActionOptions() {
        return new GeneiousActionOptions("Find SNPs",
                "Finds a SNP and generates a labelled annotation")
                .setMainMenuLocation(GeneiousActionOptions.MainMenu.AnnotateAndPredict);
    }

    public String getHelp() {
        return "This plugin generates SNP mutation annotations for a source and sequence.";
    }

    public Options getOptions(AnnotatedPluginDocument[] documents,
        SelectionRange selectionRange) throws DocumentOperationException {
        // providing no options to user, likely will change in future
        return null;
    }

    public DocumentSelectionSignature[] getSelectionSignatures() {
        return new DocumentSelectionSignature[] {
                // give this accepts an alignment, change of NucleotideSequenceDocument -> SequenceAlignmentDocument
                new DocumentSelectionSignature(SequenceAlignmentDocument.class, 1, 1)
        };
    }

    public List<List<SequenceAnnotation>> generateAnnotations(
            AnnotatedPluginDocument[] documents,
            SequenceAnnotationGenerator.SelectionRange selectionRange,
            ProgressListener progressListener,
            Options options) throws DocumentOperationException {

        // extract alignment doc
        SequenceAlignmentDocument alignment =
            (SequenceAlignmentDocument) documents[0].getDocument();

        // extract sequences
        String refAligned = alignment.getSequence(0).getSequenceString().toUpperCase();
        String queryAligned = alignment.getSequence(1).getSequenceString().toUpperCase();

        // get annotations from reference sequence (index 0)
        List<SequenceAnnotation> refAnnotations = 
            alignment.getSequence(0).getSequenceAnnotations();

        // separate forward and reverse annotations with their intervals
        // each entry is [start, end]
        List<int[]> forwardIntervals = new ArrayList<>();
        List<int[]> reverseIntervals = new ArrayList<>();

        for (SequenceAnnotation annotation : refAnnotations) {
            SequenceAnnotationInterval interval = annotation.getIntervals().get(0);
            int start = interval.getMinimumIndex();
            int end = interval.getMaximumIndex();
            SequenceAnnotationInterval.Direction direction = interval.getDirection();
            // here, we assume isReverse to be true when the annotation points left (isDirectedLeft)
            // this also assumes an unreversed sequence (eg. (Reversed) does not appear in sequence name)
            boolean isReverse = direction.isDirectedLeft();

            if (isReverse) {
                reverseIntervals.add(new int[]{start, end});
            } else {
                forwardIntervals.add(new int[]{start, end});
            }
        }

        // find query coverage region
        int queryStart = 0;
        while (queryStart < queryAligned.length() && queryAligned.charAt(queryStart) == '-') {
            queryStart++;
        }
        int queryEnd = queryAligned.length() - 1;
        while (queryEnd >= 0 && queryAligned.charAt(queryEnd) == '-') {
            queryEnd--;
        }

        // detect SNPs on full sequences
        List<Mutation> mutations = MutationDetector.detectSNPs(refAligned, queryAligned);

        StringBuilder sb = new StringBuilder();
        sb.append("queryStart=").append(queryStart).append("\n");
        for (int i = 0; i < Math.min(5, mutations.size()); i++) {
            Mutation m = mutations.get(i);
            sb.append("pos=").append(m.getPosition())
            .append(" ref=").append(m.getRefBase())
            .append(" alt=").append(m.getAltBase()).append("\n");
        }
        throw new DocumentOperationException(sb.toString());

        // filter mutations to query coverage region
        /* List<Mutation> filteredMutations = new ArrayList<>();
        for (Mutation m : mutations) {
            int pos = m.getPosition() - 1; // 0-based
            if (pos >= queryStart && pos <= queryEnd) {
                filteredMutations.add(m);
            }
        }

        // compile forward annotations
        List<SequenceAnnotation> mutationAnnotations = new ArrayList<>();
        for (Mutation m : filteredMutations) {
            int pos = m.getPosition() - 1; // 0-based

            // check if mutation falls within a forward interval
            boolean inForward = false;
            for (int[] interval : forwardIntervals) {
                if (pos >= interval[0] - 1 && pos <= interval[1] - 1) {
                    inForward = true;
                    break;
                }
            }
            // check if mutation falls within a reverse interval
            boolean inReverse = false;
            for (int[] interval : reverseIntervals) {
                if (pos >= interval[0] - 1 && pos <= interval[1] - 1) {
                    inReverse = true;
                    break;
                }
            }

            // skip if not in any annotated region
            if (!inForward && !inReverse) continue;

            SequenceAnnotationInterval position;
            if (inReverse) {
                // reverse strand interval
                position = new SequenceAnnotationInterval(
                    m.getPosition() + 1,
                    m.getPosition());
            } else {
                // forward strand interval
                position = new SequenceAnnotationInterval(
                    m.getPosition(),
                    m.getPosition());
            }

            SequenceAnnotation annotation = new SequenceAnnotation(
                m.getDescription(),
                SequenceAnnotation.TYPE_POLYMORPHISM,
                position);

            // optional qualifiers added
            annotation.addQualifier(SequenceAnnotationQualifier.VARIANT_CHANGE,
                m.getDescription());
            annotation.addQualifier(SequenceAnnotationQualifier.VARIANT_NUCLEOTIDES,
                m.getAltBase());
            annotation.addQualifier(SequenceAnnotationQualifier.VARIANT_REFERENCE_NUCLEOTIDES,
                m.getRefBase());

            mutationAnnotations.add(annotation);
        }

        // must return two lists for method to work 
        List<SequenceAnnotation> emptyList = new ArrayList<>();
        return Arrays.asList(emptyList, mutationAnnotations); */
    }
}