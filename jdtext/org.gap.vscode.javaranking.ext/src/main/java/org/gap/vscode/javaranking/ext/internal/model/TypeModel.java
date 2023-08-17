package org.gap.vscode.javaranking.ext.internal.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.gap.vscode.javaranking.ext.internal.ExtensionActivator;

public final class TypeModel {
    private Map<String, Integer> scores = new HashMap<>();
    
    private static TypeModel model = new TypeModel();
    
    private TypeModel() {
    }
    
    public void load(List<URL> urls) {
        urls.forEach(this::loadModel);
    }
    
    public static TypeModel getModel() {
        return model;
    }
    
    private void loadModel(URL url) {
        try (var fbr = new BufferedReader(new InputStreamReader(url.toURI().toURL().openStream()))) {
            fbr.lines().forEach(line -> {
                final String[] pair = line.split("=");
                if (pair.length == 2) {
                    scores.put(pair[0], Integer.parseInt(pair[1]));
                }
            });
        } catch (IOException | URISyntaxException e) {
            ExtensionActivator.logError(e.getMessage(), e);
        }
    }
    
    public Optional<Integer> findScore(String typeSig, String scope, String token) {
        final Supplier<Optional<Integer>> lowerCaseMatch = () -> Optional.ofNullable(
                scores.get(scoreKey(typeSig, scope, token.toLowerCase())));
        return Optional.ofNullable(scores.get(scoreKey(typeSig, scope, token))).or(lowerCaseMatch).or(
                () -> partialMatch(typeSig, scope, token.toLowerCase()));
    }
    
    private Optional<Integer> partialMatch(String typeSig, String scope, String token) {
        final String prefix = typeSig.concat("/").concat(scope).concat("/");
        return scores.keySet().stream().parallel().filter(key -> key.startsWith(prefix)).filter(key -> {
            int index = key.lastIndexOf("/");
            String tokenPart = key.substring(index + 1).toLowerCase();
            return tokenPart.startsWith(token);
        }).findFirst().map(key -> scores.get(key));
    }
    
    private static String scoreKey(String typeSig, String scope, String token) {
        return typeSig.concat("/").concat(scope).concat("/").concat(token);
    }
}
