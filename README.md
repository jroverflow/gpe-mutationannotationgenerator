# Mutation Annotation Generator
Simple Geneious Prime Extension automatically creating SNP annotations

## Download

| Version | Release Date |
|---|---|
|[0.3](https://github.com/jroverflow/gpe-mutationannotationgenerator/releases/tag/0.3_Release) | May 17, 2026 |
| [0.2](https://github.com/jroverflow/gpe-mutationannotationgenerator/releases/download/Releases/MutationAnnotationGenerator.gplugin) | May 6,2026 |

Keep up to date with updates with the [updates log](CHANGELOG.md)

## Installation to Geneious

This tool will typically appear as "Find SNPs" under the "Annotate & Predict" tab of Geneious Prime, unless otherwise noted via installation

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






## License

This work is licensed under a MIT License.

