package org.jenkinsci.plugins.workflow.support.steps.build;

import hudson.AbortException;
import hudson.Extension;
import hudson.model.Queue;
import hudson.model.queue.QueueListener;

/**
 * @author Vivek Pandey
 */
@Extension
public class BuildQueueListener extends QueueListener {
    @Override
    public void onLeft(Queue.LeftItem li) {
        if(li.isCancelled()){
            for (BuildTriggerAction.Trigger trigger : BuildTriggerAction.triggersFor(li)) {
                trigger.context.onFailure(new AbortException("Build of " + li.task.getFullDisplayName() + " was cancelled"));
            }
        }
    }


}
