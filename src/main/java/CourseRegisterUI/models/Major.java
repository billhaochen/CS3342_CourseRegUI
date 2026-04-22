package CourseRegisterUI.models;

public enum Major {

    // Biomedicine / Science
    BIOLOGICAL_SCIENCES,
    BIOMEDICAL_SCIENCES,
    CHEMISTRY,
    PHYSICS,
    ENVIRONMENTAL_SCIENCE,
    FOOD_SAFETY_AND_TECHNOLOGY,

    // Business
    ACCOUNTANCY,
    GLOBAL_BUSINESS,
    BUSINESS_ECONOMICS,
    FINANCE,
    MARKETING,
    MANAGEMENT,
    INFORMATION_MANAGEMENT,
    QUANTITATIVE_FINANCE,
    COMPUTATIONAL_FINANCE_AND_FINTECH,

    // Computing / Data / AI
    COMPUTER_SCIENCE,
    DATA_SCIENCE,
    ARTIFICIAL_INTELLIGENCE,
    INFORMATION_ENGINEERING,
    COMPUTER_AND_DATA_ENGINEERING,
    MULTIMEDIA_COMPUTING,
    SOFTWARE_ENGINEERING,

    // Engineering
    ARCHITECTURAL_ENGINEERING,
    CIVIL_ENGINEERING,
    ARCHITECTURE_AND_SURVEYING,
    ELECTRONIC_AND_ELECTRICAL_ENGINEERING,
    MECHANICAL_ENGINEERING,
    AEROSPACE_ENGINEERING,
    NUCLEAR_AND_RISK_ENGINEERING,
    MATERIALS_ENGINEERING,

    // Liberal Arts & Social Sciences
    ENGLISH_STUDIES,
    LINGUISTICS_AND_LANGUAGE_STUDIES,
    MEDIA_AND_COMMUNICATION,
    PSYCHOLOGY,
    PUBLIC_POLICY_AND_POLITICS,
    ASIAN_AND_INTERNATIONAL_STUDIES,
    SOCIAL_WORK,

    // Creative Media
    CREATIVE_MEDIA,
    DIGITAL_TELEVISION_AND_BROADCASTING,
    ANIMATION_AND_VISUAL_EFFECTS,

    // Energy & Environment
    ENERGY_SCIENCE_AND_ENGINEERING,
    ENVIRONMENTAL_POLICY_AND_MANAGEMENT,

    // Law
    LAW,

    // Veterinary / Life Sciences
    BIO_VETERINARY_SCIENCE,
    VETERINARY_MEDICINE;

    public String displayName() {
        return switch (this) {
            case BIOLOGICAL_SCIENCES -> "BSc Biological Sciences";
            case BIOMEDICAL_SCIENCES -> "BSc Biomedical Sciences";
            case CHEMISTRY -> "BSc Chemistry";
            case PHYSICS -> "BSc Physics";
            case ENVIRONMENTAL_SCIENCE -> "BSc Environmental Science";
            case FOOD_SAFETY_AND_TECHNOLOGY -> "BSc Food Safety and Technology";

            case ACCOUNTANCY -> "BBA Accountancy";
            case GLOBAL_BUSINESS -> "BBA Global Business";
            case BUSINESS_ECONOMICS -> "BBA Business Economics";
            case FINANCE -> "BBA Finance";
            case MARKETING -> "BBA Marketing";
            case MANAGEMENT -> "BBA Management";
            case INFORMATION_MANAGEMENT -> "BBA Information Management";
            case QUANTITATIVE_FINANCE -> "BSc Quantitative Finance";
            case COMPUTATIONAL_FINANCE_AND_FINTECH ->
                    "BSc Computational Finance and Financial Technology";

            case COMPUTER_SCIENCE -> "BSc Computer Science";
            case DATA_SCIENCE -> "BSc Data Science";
            case ARTIFICIAL_INTELLIGENCE -> "BSc Artificial Intelligence";
            case INFORMATION_ENGINEERING -> "BEng Information Engineering";
            case COMPUTER_AND_DATA_ENGINEERING -> "BEng Computer and Data Engineering";
            case MULTIMEDIA_COMPUTING -> "BSc Multimedia Computing";
            case SOFTWARE_ENGINEERING -> "BSc Software Engineering and Project Management";

            case ARCHITECTURAL_ENGINEERING -> "BEng Architectural Engineering";
            case CIVIL_ENGINEERING -> "BEng Civil Engineering";
            case ARCHITECTURE_AND_SURVEYING -> "BSc Architecture and Surveying";
            case ELECTRONIC_AND_ELECTRICAL_ENGINEERING -> "BEng Electronic and Electrical Engineering";
            case MECHANICAL_ENGINEERING -> "BEng Mechanical Engineering";
            case AEROSPACE_ENGINEERING -> "BEng Aerospace Engineering";
            case NUCLEAR_AND_RISK_ENGINEERING -> "BEng Nuclear and Risk Engineering";
            case MATERIALS_ENGINEERING -> "BEng Materials Engineering";

            case ENGLISH_STUDIES -> "BA English";
            case LINGUISTICS_AND_LANGUAGE_STUDIES -> "BA Linguistics and Language Studies";
            case MEDIA_AND_COMMUNICATION -> "BA Media and Communication";
            case PSYCHOLOGY -> "BSocSc Psychology";
            case PUBLIC_POLICY_AND_POLITICS -> "BSocSc Public Policy and Politics";
            case ASIAN_AND_INTERNATIONAL_STUDIES -> "BSocSc Asian and International Studies";
            case SOCIAL_WORK -> "BSocSc Social Work";

            case CREATIVE_MEDIA -> "BA Creative Media";
            case DIGITAL_TELEVISION_AND_BROADCASTING -> "BA Digital Television and Broadcasting";
            case ANIMATION_AND_VISUAL_EFFECTS -> "BSc/BA Animation and Visual Effects";

            case ENERGY_SCIENCE_AND_ENGINEERING -> "BEng Energy Science and Engineering";
            case ENVIRONMENTAL_POLICY_AND_MANAGEMENT -> "BSocSc Environmental Policy and Management";

            case LAW -> "LLB Law";

            case BIO_VETERINARY_SCIENCE -> "BSc BioVeterinary Science";
            case VETERINARY_MEDICINE -> "Bachelor of Veterinary Medicine";
        };
    }
}