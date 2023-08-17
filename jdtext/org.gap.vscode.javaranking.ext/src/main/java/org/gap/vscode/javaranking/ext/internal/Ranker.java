package org.gap.vscode.javaranking.ext.internal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.CompletionContext;
import org.eclipse.jdt.core.CompletionProposal;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.ls.core.contentassist.CompletionRanking;

@SuppressWarnings("restriction")
public abstract class Ranker {
    private Ranker head;
    
    public final Ranker add(Ranker ranker) {
        if (ranker.head == null) {
            ranker.head = this;
            return ranker;
        }
        throw new IllegalStateException("ranker already has a head");
    }
    
    protected abstract void performRanking(CompletionProposal proposal, CompletionRanking ranking,
            CompletionContext context, ICompilationUnit unit, IProgressMonitor monitor);
    
    protected void performInit(CompletionContext context, ICompilationUnit cu, IProgressMonitor monitor) {
    }
    
    protected void performCleanup(CompletionContext context, ICompilationUnit cu, IProgressMonitor monitor) {
    }
    
    public final void rank(CompletionProposal proposal, CompletionRanking ranking, CompletionContext context,
            ICompilationUnit cu, IProgressMonitor monitor) {
        this.performRanking(proposal, ranking, context, cu, monitor);
        if (!monitor.isCanceled() && this.head != null) {
            this.head.rank(proposal, ranking, context, cu, monitor);
        }
    }
    
    public final void init(CompletionContext context, ICompilationUnit cu, IProgressMonitor monitor) {
        this.performInit(context, cu, monitor);
        if (!monitor.isCanceled() && this.head != null) {
            this.head.performInit(context, cu, monitor);
        }
    }
    
    public final void cleanup(CompletionContext context, ICompilationUnit cu, IProgressMonitor monitor) {
        this.performCleanup(context, cu, monitor);
        if (this.head != null) {
            this.head.performCleanup(context, cu, monitor);
        }
    }
}
