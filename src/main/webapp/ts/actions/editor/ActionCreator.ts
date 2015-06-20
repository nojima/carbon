import { Promise } from 'es6-promise';
import { Action, ActionType, ChangeViewAction } from './Actions';
import { Dispatcher } from '../../dispatchers/editor/Dispatcher';
import { ViewType } from '../../components/editor/ViewType';


export class ActionCreator {
    private dispatcher: Dispatcher;
    private markdownRenderer: (text: string) => Promise<string>;

    constructor(dispatcher: Dispatcher, renderer?: ((text: string) => Promise<string>)) {
        this.dispatcher = dispatcher;
        this.markdownRenderer = renderer ? renderer : renderMarkdown;
    }

    public changeView(nextViewType: ViewType, text?: string): Promise<void> {
        let dispatcher: Dispatcher = this.dispatcher;

        if (nextViewType === ViewType.MARKDOWN) {
            return this.markdownRenderer(text).then((html: string): void => {
                dispatcher.dispatch(new ChangeViewAction(nextViewType, html));
            });
        }

        this.dispatcher.dispatch(new ChangeViewAction(nextViewType));
        return undefined;
    }

    public submit(text: string): void {
        // todo: immplementation
        alert('submitted!');
    }
}


function renderMarkdown(text: string): Promise<string> {
    'use strict';

    return new Promise<string>((resolve: Function, reject: Function): void => {
        let xhr: XMLHttpRequest = new XMLHttpRequest();
        xhr.onreadystatechange = (): void => {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    resolve(JSON.parse(xhr.responseText).html);
                } else {
                    reject('XHR Error');
                }
            }
        };
        xhr.open('POST', '/api/markdown/render.json');
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(JSON.stringify({'text': text}));
    });
}
