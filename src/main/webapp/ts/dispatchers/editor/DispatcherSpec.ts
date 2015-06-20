import Mocha = require('mocha');
import assert = require('power-assert');
import { Action, ActionType } from '../../actions/editor/Actions';
import { Dispatcher } from './Dispatcher';

describe('Dispatcher', (): void => {
    var sut: Dispatcher;

    beforeEach((): void => {
        sut = new Dispatcher();
    });

    it('registerHandlerが動作する', (done: MochaDone): void => {
        // setUp
        sut.registerHandler(ActionType.CHANGE_VIEW, (action: Action): void => {
            done();
        });

        // exercise & verify
        sut.dispatch(new Action(ActionType.CHANGE_VIEW));
    });
});
