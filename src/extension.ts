import * as vscode from 'vscode';

const JAVA_WORKSPACE_COMMAND = "java.execute.workspaceCommand";
export type JavaExt = any;

export function activate(context: vscode.ExtensionContext) {
    waitForJavaExtension().then((success) => {
        if (success) {
            vscode.commands.executeCommand(JAVA_WORKSPACE_COMMAND, "java.ranking.register", JSON.stringify([]));
        } else {
            vscode.window.showErrorMessage("Fail to register Java Ranking provider");
        }
    });
}

export function deactivate() {
    vscode.commands.executeCommand(JAVA_WORKSPACE_COMMAND, "java.ranking.unregister", JSON.stringify([]));
}

async function waitForJavaExtension(): Promise<boolean> {
    const javaExt = vscode.extensions.getExtension("redhat.java");
    if (!javaExt) {
        return false;
    }

    const api: JavaExt = await javaExt.activate();
    if (api && api.serverMode !== "Standard") {
        return new Promise<boolean>((resolve) => {
            api.onDidServerModeChange((mode: string) => {
                if (mode === "Standard") {
                    resolve(true);
                }
            });
        });
    } else if (api) {
        return true;
    } else {
        return false;
    }
} 