package com.dejavu.utopia.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {

    protected T binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBinding(inflater, container);
        return Objects.requireNonNull(binding).getRoot();
    }

    private void initBinding(LayoutInflater inflater, ViewGroup container) {
        Type superClass = getClass().getGenericSuperclass();

        if (superClass instanceof ParameterizedType) {
            Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
            Class<T> classType = (Class<T>) type;
            try {
                Method method = classType.getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
                binding = (T) method.invoke(null, inflater, container, false);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Unable to inflate ViewBinding", e);
            }
        } else {
            throw new RuntimeException("Missing type parameter.");
        }
    }

    // 如果需要，也可以在此处添加类似于 BaseActivity 中的 ProgressDialog 管理方法
    // 例如加载对话框的显示与隐藏方法，但请注意调整方法以适应Fragment的生命周期和调用方式
}