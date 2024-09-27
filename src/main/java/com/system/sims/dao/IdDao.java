package com.system.sims.dao;

import com.system.sims.beans.Id;

public interface IdDao {

    Id getId();

    int setId(Id id);

    int setDay(int day);

}
