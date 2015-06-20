import Mocha = require('mocha');
import assert = require('power-assert');
import { ChangeViewAction } from '../../actions/editor/Actions';
import { Store } from './Store';
import { Dispatcher } from '../../dispatchers/editor/Dispatcher';
import { ViewType } from '../../components/editor/ViewType';

describe('Store', (): void => {
    var sut: Store;
    var dispatcher: Dispatcher = new Dispatcher();

    beforeEach((): void => {
        sut = new Store(dispatcher);
    });

    describe('ChangeViewActionでstateが更新される', (): void => {
        it('ViewType.INPUTの場合', (): void => {
            // exercise
            dispatcher.dispatch(new ChangeViewAction(ViewType.INPUT));

            // verify
            assert(sut.getState().currentViewType === ViewType.INPUT);
        });

        it('ViewType.INPUT以外の場合', (): void => {
            // exercise
            dispatcher.dispatch(new ChangeViewAction(ViewType.MARKDOWN, 'hoge'));

            // verify
            let state: any = sut.getState();
            assert(state.currentViewType === ViewType.MARKDOWN);
            assert(state.html === 'hoge');
        });
    });
});
