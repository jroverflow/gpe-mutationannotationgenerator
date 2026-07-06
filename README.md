# Mutation Annotation Generator
Simple Geneious Prime Extension automatically creating SNP annotations. 

## Download

| Version | Release Date |
|---|---|
|[0.4](https://github.com/jroverflow/gpe-mutationannotationgenerator/releases/tag/0.4_Release) | July 5, 2026


Keep up to date with updates with the [updates log](CHANGELOG.md)

## Installation to Geneious

This tool will appear as "Find SNPs" under the "Annotate & Predict" tab of Geneious Prime

1. In Geneious Prime, navigate to the "Tools" window
2. Click "Plugin"
3. Click "Install plugin from a gplugin file"
4. Select "MutationAnnotatorGenerator.gplugin" for installation


## Usage Walkthrough

This tool works on contigs that are mapped to reference (eg. a NGS GenBank sequence to a predicted sequence), which is denoted by the stacked red lines icon. If this is not the case, create an alignment via:

1. Select file wished to be annotated
2. In the "Align/Assemble" Dropdown, select "Map to Reference(s)"
3. Choose predicted sequence to compare against
4. Click "Ok" to create alignment

#### Assuming Alignment is Already Made:

1. In the alignment file, select "Annotate & Predict"
2. Select "Find SNPs" (will likely be at the very bottom)
3. If successful, message "Found *n* results" will be displayed
4. Click "Save"
5. (Optional) A pop-up asking to apply changes to the original sequence will appear, select "Yes" to transfer these changes

#### Important Things to Note:

This tool assumes a few things: 
- Annotated regions on the sequence are the areas of interest; unannotated regions will not be accounted for
- User has thoroughly annotated sequence(s)
- Alignment given to tool are not reversed, or aligned to mismatching sequence(s)






## License

This work is licensed under a MIT License.

