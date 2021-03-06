package pro.belbix.ethparser.utils.recalculation;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pro.belbix.ethparser.dto.HarvestDTO;
import pro.belbix.ethparser.entity.BlockCacheEntity;
import pro.belbix.ethparser.repositories.BlockCacheRepository;
import pro.belbix.ethparser.repositories.HarvestRepository;

@Service
@Log4j2
public class BlockCacher {

    private final HarvestRepository harvestRepository;
    private final BlockCacheRepository blockCacheRepository;

    public BlockCacher(HarvestRepository harvestRepository,
                       BlockCacheRepository blockCacheRepository) {
        this.harvestRepository = harvestRepository;
        this.blockCacheRepository = blockCacheRepository;
    }

    public void cacheBlocks() {
        List<HarvestDTO> harvestDTOS = harvestRepository.findAll();
        List<BlockCacheEntity> blockCacheEntities = new ArrayList<>();
        int count = 0;
        int bulkSize = 100;
        for (HarvestDTO dto : harvestDTOS) {
            count++;
            long block = dto.getBlock().longValue();
            if (!blockCacheRepository.existsById(block)) {
                BlockCacheEntity blockCacheEntity = new BlockCacheEntity();
                blockCacheEntity.setBlock(block);
                blockCacheEntity.setBlockDate(dto.getBlockDate());
                blockCacheEntities.add(blockCacheEntity);
            }
            if (blockCacheEntities.size() % bulkSize == 0) {
                log.info("Save block caches " + (count * bulkSize));
                blockCacheRepository.saveAll(blockCacheEntities);
                blockCacheEntities.clear();
            }
        }

    }

}
