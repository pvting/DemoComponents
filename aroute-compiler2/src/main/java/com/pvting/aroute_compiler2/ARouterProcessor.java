package com.pvting.aroute_compiler2;


import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

@AutoService(Processor.class)//告诉gradle你是个process
@SupportedAnnotationTypes({"com.pvting.aroute_annotations2.ARouter"})//告诉process，我要看哪个注解
@SupportedSourceVersion(SourceVersion.RELEASE_7)//环境版本
public class ARouterProcessor extends AbstractProcessor {
    //操作class中的成员
    private Elements elements;
    //更多细节
    private Types types;
    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        elements = processingEnv.getElementUtils();
        messager=processingEnv.getMessager();
        filer=processingEnv.getFiler();
        types=processingEnv.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }
}
