/**
 * This C++ file demonstrates array index out-of-bounds vulnerabilities,
 * with comments showing how they would be tagged by a static analysis tool.
 *
 * The primary tags for these issues are CWE and OWASP.
 *
 * NOTE: SPRING is a Java framework and is not applicable here.
 * WASP (Web Application Security Project) is less specific for this low-level
 * memory issue, but an out-of-bounds vulnerability could be part of a larger
 * web-based attack, so we'll show a combined example.
 */

#include <iostream>
#include <cstring>

// A simple function to simulate a web request with user input.
// This is not a proper web server, just a demonstration of input flow.
void handle_request(char* input) {
    char buffer[10];
    
    // Combination 1: (CWE AND OWASP)
    // Issue: Out-of-bounds write. This is a buffer overflow that can be
    // exploited to inject malicious code.
    // Tags: CWE, OWASP
    // CWE-787: Out-of-bounds Write
    // OWASP A03:2021: Injection
    // The following line copies user input directly into a fixed-size buffer
    // without checking the length, causing a buffer overflow if input is too long.
    strcpy(buffer, input);

    std::cout << "Data copied to buffer: " << buffer << std::endl;
}

void process_data() {
    int data[5] = {10, 20, 30, 40, 50};
    int index = 7;
    
    // Combination 2: (CWE only)
    // Issue: Out-of-bounds read. Accessing an array element outside of its
    // defined boundaries. This is a pure memory safety issue.
    // Tags: CWE
    // CWE-125: Out-of-bounds Read
    // OWASP is not directly applicable unless this leads to a larger attack.
    int value = data[index];
    
    std::cout << "Reading out of bounds: " << value << std::endl;
}

void process_web_payload() {
    int payload[5] = {0, 0, 0, 0, 0};
    int index = 6;
    int payload_value = 12345;
    
    // Combination 3: (CWE AND WASP)
    // Issue: Out-of-bounds write in a web context. This could be part of a
    // larger web application attack, making WASP tags relevant.
    // Tags: CWE, WASP
    // CWE-787: Out-of-bounds Write
    // WASP: The Web Application Security Project might tag this as a
    // general vulnerability related to input validation or a specific attack pattern.
    payload[index] = payload_value;
    
    std::cout << "Writing to a web payload array out of bounds." << std::endl;
}

// NOTE: We cannot create an issue with the "SPRING" tag here, as it
// is a Java framework and not relevant to C++ code. The static analysis
// tool would not apply this tag.

int main(int argc, char* argv[]) {
    
    // CWE and OWASP combined
    char long_input[20] = "ThisIsTooLong";
    handle_request(long_input);

    // CWE only
    process_data();
    
    // CWE and WASP combined
    process_web_payload();

    return 0;
}
