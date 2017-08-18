package com.TinyUrl.Services;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.framework.recipes.shared.SharedCountListener;
import org.apache.curator.framework.recipes.shared.SharedCountReader;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.TinyUrl.Models.Range;

@Service
public class SharedRangeService implements SharedCountListener {
	private static final String ZOO_SERVER = "localhost:2181";
	private static final String COUNTER_PATH = "/TinyUrl/counter";
	private static final long SPAN = 10;

	private CuratorFramework client;
	private DistributedAtomicLong count;
	
	@PostConstruct
	private void postConstruct()
	{
		client = CuratorFrameworkFactory.newClient(ZOO_SERVER,
				new ExponentialBackoffRetry(1000, 3));
		client.start();
		count = new DistributedAtomicLong(client, COUNTER_PATH,
				new RetryNTimes(10, 10));
	}
	
	@Bean("range")
	public Range getUniqueRange() {
		try 
		{
			
			if(client == null) postConstruct();
			AtomicValue<Long> val = count.increment();
			if (val.succeeded())
			{
				long start = val.preValue()*SPAN;		
				return new Range(start,start+SPAN-1);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void stateChanged(CuratorFramework client, ConnectionState newState) {

	}

	@Override
	public void countHasChanged(SharedCountReader sharedCount, int newCount) throws Exception {
		// TODO Auto-generated method stub

	}

}
