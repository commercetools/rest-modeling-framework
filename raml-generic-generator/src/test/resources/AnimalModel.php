<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

class AnimalModel extends JsonObject implements Animal {
    const DISCRIMINATOR_VALUE = '';


    public function __construct(array $data = []) {
        parent::__construct($data);
        $this->kind = static::DISCRIMINATOR_VALUE;
    }

    /**
     * @var string
     */
    private $kind;

    /**
     * @return string
     */
    public function getKind() { return $this->kind; }
}
