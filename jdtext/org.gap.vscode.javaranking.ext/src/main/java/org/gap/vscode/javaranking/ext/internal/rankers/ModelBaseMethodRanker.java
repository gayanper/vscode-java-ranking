package org.gap.vscode.javaranking.ext.internal.rankers;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.CompletionContext;
import org.eclipse.jdt.core.CompletionProposal;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.ls.core.contentassist.CompletionRanking;
import org.gap.vscode.javaranking.ext.internal.ProjectHelper;
import org.gap.vscode.javaranking.ext.internal.Ranker;
import org.gap.vscode.javaranking.ext.internal.model.MethodModel;

@SuppressWarnings("restriction")
public class ModelBaseMethodRanker extends Ranker {
    @Override
    protected void performRanking(CompletionProposal proposal, CompletionRanking ranking, CompletionContext context,
            ICompilationUnit cu, IProgressMonitor monitor) {
        if (proposal.getKind() != CompletionProposal.METHOD_REF) {
            return;
        }
        
        final String scope = ProjectHelper.isTestSource(cu) ? "T" : "S";
        final String name = String.valueOf(proposal.getName());
        final String signature = String.valueOf(proposal.getSignature());
        final String typeSignature = String.valueOf(Signature.getTypeErasure(proposal.getDeclarationSignature()));
        
        MethodModel.getModel().findScore(typeSignature, signature, name, scope).ifPresent(score -> {
            ranking.setScore(score);
            ranking.setDecorator('*');
        });
        
    }
    
}
