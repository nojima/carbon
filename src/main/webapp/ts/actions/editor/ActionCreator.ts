import { Promise } from 'es6-promise';
import { Action, ActionType, ChangeViewAction } from './Actions';
import { Dispatcher } from '../../dispatchers/editor/Dispatcher';
import { ViewType } from '../../components/editor/ViewType';

export class ActionCreator {
    private dispatcher: Dispatcher;

    constructor(dispatcher: Dispatcher) {
        this.dispatcher = dispatcher;
    }

    public changeView(nextViewType: ViewType, text: string): void {
        if (nextViewType === ViewType.MARKDOWN) {
            renderMarkdown(text).then((function(html: string): void {
                this.dispatcher.dispatch(
                    new ChangeViewAction(nextViewType, html));
            }).bind(this));
        } else {
            this.dispatcher.dispatch(
                new ChangeViewAction(nextViewType));
        }
    }

    public submit(text: string): void {
        // todo: immplementation
        alert('submitted!');
    }
}


function renderMarkdown(text: string): Promise<string> {
    'use strict';

    return new Promise<string>(function(
            resolve: Function, reject: Function): void {
        // todo: あとでサーバに投げる
        resolve(text);
    });
}
