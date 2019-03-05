package com.github.windsekirun.daggerautoinjectkt;

import com.github.windsekirun.daggerautoinjectkt.compiler.*;
import com.github.windsekirun.daggerautoinjectkt.compiler.data.InvestTarget;
import com.github.windsekirun.daggerautoinjectkt.holder.ApplicationHolder;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.HashSet;
import java.util.Set;

/**
 * DaggerAutoInjectKt
 * Class: AutoInjectJCompiler
 * Created by Pyxis on 2019-03-05.
 * <p>
 * Description:
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions("kapt.kotlin.generated")
public class AutoInjectJCompiler extends AbstractProcessor {
    private InvestTarget investTarget = new InvestTarget();

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (roundEnvironment != null) investTarget.invest(roundEnvironment);
        constructClass();
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> sets = new HashSet<>();
        sets.add(InjectActivity.class.getName());
        sets.add(InjectApplication.class.getName());
        sets.add(InjectBroadcastReceiver.class.getName());
        sets.add(InjectContentProvider.class.getName());
        sets.add(InjectFragment.class.getName());
        sets.add(InjectService.class.getName());
        sets.add(InjectViewModel.class.getName());
        return sets;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    private void constructClass() {
        ApplicationHolder applicationHolder = investTarget.getApplicationHolder();
        if (applicationHolder == null) return;

        new ActivityCompiler().construct(processingEnv, investTarget.getActivityHolder());
        new ServiceCompiler().construct(processingEnv, investTarget.getServiceHolder());
        new FragmentCompiler().construct(processingEnv, investTarget.getFragmentHolder());
        new BroadcastReceiverCompiler().construct(processingEnv, investTarget.getBroadcastHolder());
        new ContentProviderCompiler().construct(processingEnv, investTarget.getContentHolder());
        new ViewModelCompiler().construct(processingEnv, investTarget.getViewModelHolder());
        new ApplicationCompiler().construct(processingEnv, applicationHolder);

        investTarget.clear();
    }
}
