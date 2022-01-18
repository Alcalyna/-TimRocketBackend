package com.example.timrocket_backend.api;

import com.example.timrocket_backend.domain.ClassTest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/classtests")
public class ClassTestController {

    private List<ClassTest> classTests = new ArrayList<>();

    @GetMapping
    @PreAuthorize("hasAnyAuthority('GET_CLASS_TEST')")
    public List<ClassTest> getAllClassTests() {
        return classTests;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADD_CLASS_TEST')")
    public ClassTest addClassTest(@RequestBody ClassTest classTest) {
        classTests.add(classTest);
        return classTest;
    }



}