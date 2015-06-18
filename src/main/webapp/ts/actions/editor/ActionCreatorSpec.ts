import Mocha = require('mocha');
import assert = require('power-assert');
import { Promise } from 'es6-promise';
import { ActionCreator } from './ActionCreator';
import { ViewType } from '../../components/editor/ViewType';
import { Dispatcher } from '../../dispatchers/editor/Dispatcher';
import { Action, ChangeViewAction } from './Actions';

// todo: まともなモックをつくる
class MockDispatcher {
    payloads: Array<any> = [];

    dispatch(payload: any): void {
        this.payloads.push(payload);
    }
}

function render(text: string): Promise<string> {
    'use strict';
    return new Promise<string>(
        function(resolve: any, reject: any): void {
        resolve('<div>' + text + '</div>');
    });
}

describe('ActionCreator', (): void => {
    var sut: ActionCreator;
    var dispatcher: MockDispatcher;

    beforeEach((): void => {
        dispatcher = new MockDispatcher();
        sut = new ActionCreator(<Dispatcher>(<any>dispatcher), render);
    });

    describe('changeViewのdispatch', (): void => {
        it('ViewType.INPUTの場合', (): void => {
            // exercise
            sut.changeView(ViewType.INPUT, 'hoge');

            // verify
            assert(dispatcher.payloads.length === 1);
            assert(dispatcher.payloads[0] instanceof ChangeViewAction);
            assert(dispatcher.payloads[0].nextViewType === ViewType.INPUT);
        });

        it('ViewType.MARKDOWNの場合', (): Promise<void> => {
            // exercise
            let promise: Promise<void> = sut.changeView(ViewType.MARKDOWN, 'hoge');

            // verify
            return promise.then((): void => {
                assert(dispatcher.payloads.length === 1);
                assert(dispatcher.payloads[0] instanceof ChangeViewAction);
                assert(dispatcher.payloads[0].nextViewType === ViewType.MARKDOWN);
                assert(dispatcher.payloads[0].html === '<div>hoge</div>');
            });
        });
    });
});
