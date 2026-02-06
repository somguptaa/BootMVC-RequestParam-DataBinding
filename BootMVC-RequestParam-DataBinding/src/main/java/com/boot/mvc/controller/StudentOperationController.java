package com.boot.mvc.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * StudentOperationController
 * 
 * MY LEARNING CONTROLLER - All @RequestParam Scenarios
 * 
 * This is where I practice everything I learned about capturing
 * URL parameters in Spring Boot MVC.
 * 
 * Spring Boot 3.x | Java 21 | Port: 4045
 * Author: Som Gupta (Learning Mode)
 */
@Controller  // tells Spring this is a controller class
public class StudentOperationController {

    /* =====================================================
     * CASE 1: BASIC REQUIRED PARAMETER BINDING
     * My Learning: Lesson 1 - Basic Parameter Binding
     * 
     * What I learned: When I type ?sno=101&sname=John in URL,
     * Spring automatically captures these values.
     * 
     * URL: http://localhost:4045/basic?sno=101&sname=John
     * 
     * Key Point: @RequestParam("sno") means "look for 'sno' in URL"
     * ===================================================== */
    @GetMapping("/basic")
    public String basicBinding(
            @RequestParam("sno") int no,        // URL param name 'sno' → method param 'no'
            @RequestParam("sname") String name   // URL param name 'sname' → method param 'name'
    ) {
        // Both are REQUIRED by default - if missing, I get 400 error
        System.out.println("CASE 1: BASIC BINDING");
        System.out.println(no + " " + name);    // Prints: 101 John
        return "result";  // returns result.jsp page
    }

    /* =====================================================
     * CASE 2: IMPLICIT PARAMETER NAMES (Cleaner Way!)
     * My Learning: Lesson 2 - When Names Match
     * 
     * What I learned: If my variable name = URL param name,
     * I don't need to specify it in @RequestParam
     * 
     * URL: http://localhost:4045/implicit?sno=101&sname=John
     * 
     * Important: Names must match EXACTLY (case-sensitive!)
     * sname ≠ sName ≠ SNAME
     * ===================================================== */
    @GetMapping("/implicit")
    public String implicitBinding(
            @RequestParam int sno,      // Spring automatically looks for ?sno=...
            @RequestParam String sname  // Spring automatically looks for ?sname=...
    ) {
        // This is cleaner - less code to write!
        System.out.println("CASE 2: IMPLICIT NAMES");
        System.out.println(sno + " " + sname);
        return "result";
    }

    /* =====================================================
     * CASE 3: OPTIONAL PARAMETER (required=false)
     * My Learning: Lesson 3 - Making Parameters Optional
     * 
     * What I learned: By default all params are REQUIRED.
     * If I want optional params, use required=false
     * 
     * URLs I tested:
     * ✅ /optional?sno=101&sname=John  → Works, prints: 101 John
     * ✅ /optional?sno=101             → Works, prints: 101 null
     * ❌ /optional?sname=John          → Error! sno is required
     * ===================================================== */
    @GetMapping("/optional")
    public String optionalParam(
            @RequestParam Integer sno,                      // REQUIRED - must be in URL
            @RequestParam(required = false) String sname    // OPTIONAL - can be missing
    ) {
        // sname can be null if not provided in URL
        System.out.println("CASE 3: OPTIONAL PARAM");
        System.out.println(sno + " " + sname);  // sname might be null
        return "result";
    }

    /* =====================================================
     * CASE 4: PRIMITIVE vs WRAPPER (My Important Discovery!)
     * My Learning: Lesson 3 - Why Use Integer Instead of int
     * 
     * What I learned: 
     * - int (primitive) → if missing, becomes 0
     * - Integer (wrapper) → if missing, becomes null
     * 
     * Problem with int: Can't tell if user entered 0 or didn't provide value
     * Solution with Integer: null clearly means "not provided"
     * 
     * URLs I tested:
     * /wrapper?age=25  → Prints: AGE: 25
     * /wrapper         → Prints: AGE: null (not 0!)
     * ===================================================== */
    @GetMapping("/wrapper")
    public String wrapperVsPrimitive(
            @RequestParam(required = false) Integer age  // Use Integer for optional params!
    ) {
        // age will be null if not provided (not 0)
        System.out.println("CASE 4: WRAPPER TYPE");
        System.out.println("AGE: " + age);
        return "result";
    }

    /* =====================================================
     * CASE 5: DEFAULT VALUES (My Favorite Feature!)
     * My Learning: Lesson 4 - Setting Default Values
     * 
     * What I learned: Instead of getting null, I can provide
     * a default value when parameter is missing.
     * 
     * Cool Discovery: defaultValue makes param optional automatically!
     * No need to write required=false
     * 
     * My test results:
     * /default                        → 0 Guest
     * /default?sno=101                → 101 Guest
     * /default?sno=101&sname=John     → 101 John
     * 
     * Real-world use: Pagination (page=1, size=10 by default)
     * ===================================================== */
    @GetMapping("/default")
    public String defaultValues(
            @RequestParam(defaultValue = "0") int sno,       // if missing, use 0
            @RequestParam(defaultValue = "Guest") String sname // if missing, use "Guest"
    ) {
        // defaultValue = optional + fallback value
        System.out.println("CASE 5: DEFAULT VALUES");
        System.out.println(sno + " " + sname);
        return "result";
    }

    /* =====================================================
     * CASE 6: MULTIPLE VALUES - This Blew My Mind!
     * My Learning: Lesson 5 - Multiple Values (Array/List/Set)
     * 
     * What I learned: Same parameter can appear multiple times!
     * Like when user selects multiple checkboxes.
     * 
     * Example: User selects Hyd, Pune, Delhi checkboxes
     * URL becomes: ?city=Hyd&city=Pune&city=Delhi
     * 
     * Spring can capture ALL values in:
     * - Array (String[])
     * - List (List<String>)
     * - Set (Set<String>)
     * 
     * URL: /multi?city=Hyd&city=Pune&city=Delhi
     * ===================================================== */
    @GetMapping("/multi")
    public String multipleValues(
            @RequestParam("city") String[] cityArray,    // Option 1: Array
            @RequestParam("city") List<String> cityList, // Option 2: List (most common)
            @RequestParam("city") Set<String> citySet    // Option 3: Set (unique values)
    ) {
        // Spring collects all 'city' values from URL
        System.out.println("CASE 6: MULTIPLE VALUES");
        System.out.println("Array : " + Arrays.toString(cityArray)); // [Hyd, Pune, Delhi]
        System.out.println("List  : " + cityList);                   // [Hyd, Pune, Delhi]
        System.out.println("Set   : " + citySet);                    // [Hyd, Pune, Delhi]
        return "result";
    }

    /* =====================================================
     * CASE 7: DUPLICATE VALUES TEST (My Experiment)
     * My Learning: Lesson 5 - What Happens with Duplicates?
     * 
     * My Question: What if same value appears twice?
     * URL: ?city=Hyd&city=Pune&city=Hyd (Hyd appears twice)
     * 
     * My Discovery:
     * - List keeps duplicates: [Hyd, Pune, Hyd]
     * - Set removes duplicates: [Hyd, Pune]
     * 
     * When to use:
     * - List: Need all values, order matters
     * - Set: Want unique values only (like tags)
     * ===================================================== */
    @GetMapping("/duplicate")
    public String duplicateTest(
            @RequestParam("city") List<String> list,  // keeps ALL values
            @RequestParam("city") Set<String> set     // removes duplicates
    ) {
        System.out.println("CASE 7: DUPLICATES");
        System.out.println("List: " + list);  // [Hyd, Pune, Hyd]
        System.out.println("Set : " + set);   // [Hyd, Pune]
        return "result";
    }

    /* =====================================================
     * CASE 8: COMMA-SEPARATED STRING (Surprise Feature!)
     * My Learning: Lesson 6 - Multiple Values as CSV
     * 
     * What I discovered: If I use String instead of List/Array,
     * Spring joins multiple values with commas!
     * 
     * URL: /csv?sno=101&sname=John&city=Hyd&city=Pune&city=Delhi
     * Output: 101 John Hyd,Pune,Delhi
     * 
     * When I use this:
     * - Storing in database as comma-separated
     * - Displaying as simple text
     * - Later I can split: cities.split(",")
     * ===================================================== */
    @GetMapping("/csv")
    public String csvValues(
            @RequestParam(required = false) Integer sno,
            @RequestParam String sname,
            @RequestParam(required = false) String city  // Gets: "Hyd,Pune,Delhi"
    ) {
        System.out.println("CASE 8: CSV VALUES");
        System.out.println(sno + " " + sname + " " + city);

        // If I need to process individual cities
        if (city != null) {
            String[] cities = city.split(",");  // Convert back to array
            for (String c : cities) {
                System.out.println("CITY: " + c.trim());  // trim() removes spaces
            }
        }
        return "result";
    }

    /* =====================================================
     * CASE 9: MIXED SCENARIO (Real-World Example)
     * My Learning: Combining Everything I Learned
     * 
     * What I'm doing: Using required + optional + default together
     * 
     * URLs I tested:
     * ✅ /mixed?sname=John              → 0 John null
     * ✅ /mixed?sno=101&sname=John      → 101 John null
     * ✅ /mixed?sno=101&sname=John&age=25 → 101 John 25
     * ❌ /mixed?sno=101                 → Error! sname is required
     * 
     * Real-world use: Search forms with filters
     * - Some filters required (like search term)
     * - Some optional (like category, price range)
     * - Some with defaults (like page number)
     * ===================================================== */
    @GetMapping("/mixed")
    public String mixedCase(
            @RequestParam(defaultValue = "0") int sno,     // optional with default
            @RequestParam String sname,                    // REQUIRED
            @RequestParam(required = false) Integer age    // optional, can be null
    ) {
        // This is how real applications work!
        System.out.println("CASE 9: MIXED");
        System.out.println(sno + " " + sname + " " + age);
        return "result";
    }
    
    /* =====================================================
     * MY TESTING CHECKLIST (What I Test for Each Method)
     * 
     * 1. ✅ Happy path - all params provided correctly
     * 2. ❌ Missing required param - should get 400 error
     * 3. ✅ Missing optional param - should work fine
     * 4. ✅ Wrong param name - should get 400 error
     * 5. ✅ Multiple values - check Array/List/Set behavior
     * 6. ✅ Duplicates - see if Set removes them
     * 7. ✅ Default values - check when param missing
     * 
     * Common Mistakes I Made:
     * - Forgot required=false → got errors
     * - Used int instead of Integer → couldn't detect null
     * - Wrong param names → spent 30 mins debugging!
     * - Case sensitivity → sname ≠ sName
     * ===================================================== */
}