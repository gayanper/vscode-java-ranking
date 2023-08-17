package org.gap.vscode.javaranking.ext.internal;

import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;

public final class ProjectHelper {
    private ProjectHelper() {
    }
    
    public static boolean isTestSource(ICompilationUnit cu) {
        return Optional.ofNullable(cu.getJavaProject()).flatMap(jp -> {
            try {
                return Optional.of(jp.getResolvedClasspath(true));
            } catch (JavaModelException e) {
                return Optional.empty();
            }
        }).filter(cps -> {
            IPath cuPath = cu.getResource().getFullPath();
            return Stream.of(cps).filter(cp -> cp.getEntryKind() == IClasspathEntry.CPE_SOURCE).filter(
                    IClasspathEntry::isTest).filter(cp -> cp.getPath().isPrefixOf(cuPath)).findFirst().isPresent();
        }).isPresent();
    }
}
