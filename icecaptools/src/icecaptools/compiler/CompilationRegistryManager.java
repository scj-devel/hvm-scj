package icecaptools.compiler;

import java.util.LinkedList;
import java.util.List;

import util.ICompilationRegistry;
import util.MethodOrFieldDesc;

public class CompilationRegistryManager implements ICompilationRegistry {

	private List<ICompilationRegistry> registries;
	
	public CompilationRegistryManager()
	{
		registries = new LinkedList<ICompilationRegistry>();
	}
	
	@Override
	public boolean didICareHuh() {
		return true;
	}

	@Override
	public boolean isMethodCompiled(MethodOrFieldDesc mdesc) {
		boolean val = false;
		for (ICompilationRegistry registry: registries)
		{
			val = registry.isMethodCompiled(mdesc);
			if (registry.didICareHuh())
			{
				return val;
			}
		}
		return val;
	}

	@Override
	public boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature) {
		boolean val = false;
		for (ICompilationRegistry registry: registries)
		{
			val = registry.isMethodExcluded(clazz, targetMethodName, targetMethodSignature);
			
			if (registry.didICareHuh())
			{
				return val;
			}
		}
		return val;
	}

	@Override
	public boolean alwaysClearOutputFolder() {
		for (ICompilationRegistry registry: registries)
		{
			boolean val = registry.alwaysClearOutputFolder();
			
			if (registry.didICareHuh())
			{
				return val;
			}
		}
		return false;
	}

	public void addRegistry(ICompilationRegistry creg) {
		registries.add(creg);
	}
}
