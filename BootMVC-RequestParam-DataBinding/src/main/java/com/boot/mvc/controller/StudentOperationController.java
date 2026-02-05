package com.boot.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Simple Spring Boot MVC Controller demonstrating @RequestParam data binding
 * 
 * This controller shows how Spring automatically binds URL query parameters
 * to method parameters using the @RequestParam annotation.
 */
@Controller
public class StudentOperationController {

    // ========================================================================
    // EXAMPLE 1: Parameter Name Mismatch - Need Explicit Mapping
    // ========================================================================
    
    /*
     * URL: http://localhost:4045/data?sno=101&sname=som
     * 
     * When request parameter name (sno) is different from method parameter name (no),
     * we MUST specify the parameter name explicitly in @RequestParam annotation.
     * 
     * @RequestParam("sno") tells Spring: "Take value from ?sno=... and put it in 'no' variable"
     * 
     * If we don't specify "sno" and just use @RequestParam int no, 
     * Spring will look for ?no=... in URL and won't find it → 400 Bad Request error
     */
    @GetMapping("/data-explicit")
    public String processStudentDataExplicit(
            @RequestParam("sno") int no,        // Maps URL parameter "sno" to variable "no"
            @RequestParam("sname") String name  // Maps URL parameter "sname" to variable "name"
    ) {
        System.out.println(no + "....." + name);
        return "result";
    }

    // ========================================================================
    // EXAMPLE 2: Parameter Name Match - Implicit Mapping
    // ========================================================================
    
    /*
     * URL: http://localhost:4045/data?sno=101&sname=som
     * 
     * When request parameter name matches method parameter name,
     * we can skip the value in @RequestParam annotation.
     * 
     * Spring automatically maps ?sno=... to int sno because names match.
     * 
     * ERROR SCENARIO:
     * If URL is: http://localhost:4045/data?sno=101&name=som (wrong param name)
     * Spring expects "sname" but gets "name" → 400 Bad Request error
     */
    @GetMapping("/data-implicit")
    public String processStudentDataImplicit(
            @RequestParam int sno,      // Implicitly maps to ?sno=...
            @RequestParam String sname  // Implicitly maps to ?sname=...
    ) {
        System.out.println(sno + "....." + sname);
        return "result";
    }

    // ========================================================================
    // EXAMPLE 3: Optional Parameters - Using required=false
    // ========================================================================
    
    /*
     * By default, @RequestParam parameters are REQUIRED.
     * If parameter is missing in URL → 400 Bad Request error
     * 
     * Solution 1: Use required=false to make parameter optional
     * 
     * URL Examples:
     * 1. http://localhost:4045/data?sno=101&sname=som  → Output: 101.....som
     * 2. http://localhost:4045/data?sno=101            → Output: 101.....null (no error!)
     * 3. http://localhost:4045/data?sno=101&name=som   → Output: 101.....null (param name mismatch but no error)
     * 
     * When parameter is missing or doesn't match:
     * - For Object types (String, Integer): value becomes null
     * - For primitive types (int, boolean): value becomes 0 or false
     * 
     * BEST PRACTICE: Use wrapper types (Integer instead of int) with required=false
     * so you can check if value is null (not provided) vs actually zero
     */
    @GetMapping("/data-optional")
    public String processStudentDataOptional(
            @RequestParam Integer sno,                          // Required by default
            @RequestParam(required = false) String sname        // Optional - won't throw error if missing
    ) {
        System.out.println(sno + "....." + sname);
        return "result";
    }

    // ========================================================================
    // EXAMPLE 4: Default Values - Using defaultValue
    // ========================================================================
    
    /*
     * Solution 2: Use defaultValue to provide fallback value for missing parameters
     * 
     * defaultValue automatically sets required=false, so parameter becomes optional.
     * 
     * URL Examples:
     * 1. http://localhost:4045/data?sno=101&sname=prakash  → Output: 101.....prakash (uses provided values)
     * 2. http://localhost:4045/data?sno=101                → Output: 101.....som (uses default for sname)
     * 3. http://localhost:4045/data?sno=101&name=prakash   → Output: 101.....som (param name mismatch, uses default)
     * 
     * Default value is used when:
     * - Parameter is not present in URL
     * - Parameter name doesn't match
     * - Parameter value is empty
     * 
     * Spring converts the defaultValue String to the target type automatically.
     * Example: defaultValue="100" → int = 100
     */
    @GetMapping("/data-default")
    public String processStudentDataDefault(
            @RequestParam(defaultValue = "0") int sno,          // If missing, sno = 0
            @RequestParam(defaultValue = "som") String sname    // If missing, sname = "som"
    ) {
        System.out.println(sno + "....." + sname);
        return "result";
    }

    // ========================================================================
    // EXAMPLE 5: Multiple Values - Array, List, Set
    // ========================================================================
    
    /*
     * When same parameter name appears multiple times in URL,
     * we can bind to Array, List, or Set to capture all values.
     * 
     * URL: http://localhost:4045/data-multi?sno=101&sname=som&sadd=hyd&sadd=pune&sadd=delhi
     *                                                            |______________|
     *                                                         Same param, 3 values
     * 
     * Binding Options:
     * 1. Array (String[]):   Preserves order, allows duplicates
     * 2. List (List<String>): Preserves order, allows duplicates  
     * 3. Set (Set<String>):   No duplicates, no guaranteed order
     * 
     * Common Use Cases:
     * - Multi-select checkboxes: <input type="checkbox" name="hobbies" value="reading">
     * - Multi-select dropdown: <select name="courses" multiple>
     * - Multiple filter values: ?category=books&category=electronics
     * 
     * Example with duplicates:
     * URL: ?sadd=hyd&sadd=pune&sadd=hyd
     * Array: [hyd, pune, hyd] - keeps all
     * List:  [hyd, pune, hyd] - keeps all
     * Set:   [hyd, pune] - removes duplicate
     */
    @GetMapping("/data-multi")
    public String processStudentDataMultiple(
            @RequestParam(name = "sno", required = false) Integer no,
            @RequestParam("sname") String name,
            @RequestParam("sadd") String[] addrsArray,      // Binds to array
            @RequestParam("sadd") List<String> addrsList,   // Binds to list
            @RequestParam("sadd") Set<String> addrsSet      // Binds to set
    ) {
        System.out.println(no + "  " + name + "  " + Arrays.toString(addrsArray) + "  " + addrsList + "  " + addrsSet);
        // Output: 101  som  [hyd, pune, delhi]  [hyd, pune, delhi]  [hyd, pune, delhi]
        return "result";
    }

    // ========================================================================
    // EXAMPLE 6: Multiple Values to Single String (Comma-Separated)
    // ========================================================================
    
    /*
     * If handler method parameter type is simple String and we pass multiple values
     * with same parameter name, Spring automatically joins them with commas.
     * 
     * URL: http://localhost:4045/data?sno=101&sname=som&sadd=hyd&sadd=pune&sadd=delhi
     * 
     * Result: addrs = "hyd,pune,delhi" (single comma-separated string)
     * 
     * This is automatic behavior - no special configuration needed.
     * 
     * To parse back to array/list:
     * String[] parts = addrs.split(",");  → ["hyd", "pune", "delhi"]
     * 
     * Use Cases:
     * - Storing CSV format in database
     * - Legacy systems expecting comma-separated values
     * - Simple display without iteration
     */
    @GetMapping("/data")
    public String processStudentData(
            @RequestParam(name = "sno", required = false) Integer no,
            @RequestParam("sname") String name,
            @RequestParam(value = "sadd", required = false) String addrs  // Multiple values → comma-separated string
    ) {
        System.out.println(no + "  " + name + "  " + addrs);
        // Output: 101  som  hyd,pune,delhi
        return "result";
    }

    /*
     * IMPORTANT NOTE FOR FORMS:
     * 
     * If your HTML form has multiple checkboxes or a list box that allows
     * selecting multiple items at a time, use Array, List, or Set type
     * in the controller method parameter to capture all selected values.
     * 
     * Example HTML:
     * <input type="checkbox" name="sadd" value="hyd"> Hyderabad
     * <input type="checkbox" name="sadd" value="pune"> Pune
     * <input type="checkbox" name="sadd" value="delhi"> Delhi
     * 
     * Controller:
     * @RequestParam("sadd") List<String> addresses
     */
}