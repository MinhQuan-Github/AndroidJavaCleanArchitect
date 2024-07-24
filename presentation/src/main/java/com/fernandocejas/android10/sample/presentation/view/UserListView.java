/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view;

import com.fernandocejas.android10.sample.presentation.model.UserModel;
import java.util.Collection;

public interface UserListView extends LoadDataView {
  void renderUserList(Collection<UserModel> userModelCollection);

  void viewUser(UserModel userModel);
}
