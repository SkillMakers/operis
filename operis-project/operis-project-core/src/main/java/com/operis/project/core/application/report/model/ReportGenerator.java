package com.operis.project.core.application.report.model;

@FunctionalInterface
public interface ReportGenerator<T, R> {
    R generate(T input);
}
