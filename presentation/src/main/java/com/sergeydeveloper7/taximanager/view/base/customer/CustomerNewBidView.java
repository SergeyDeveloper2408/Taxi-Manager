package com.sergeydeveloper7.taximanager.view.base.customer;

import com.sergeydeveloper7.data.models.map.response.StepResponse;

public interface CustomerNewBidView {

    //TODO fake methods, need, because Google Directions API is not free.
    void fakeShowFindDirectionProcessStart();
    void fakeShowFindDirectionProcessEnd(StepResponse response);

    //TODO real methods, need to restore, when Google Directions API will be free.
//    void showFindDirectionProcessStart();
//    void showFindDirectionProcessEnd();
//    void showFindDirectionProcessError(Throwable throwable);

}